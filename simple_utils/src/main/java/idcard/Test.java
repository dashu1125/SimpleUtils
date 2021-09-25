package idcard;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static Pattern compile = Pattern.compile("([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])");
    public static final int radio = 6378137;

	public static void main(String[] args) {
	    //身份证打码
//        idCardTest();

        //获取字符串中的数字和小数点
        String s = numTest("一共120.25万人");
        //通过经纬度获取距离(单位：千米)
        double distance = distanceTest(121.444454, 31.285425, 121.687321, 31.202995);


    }
    /**
     * 身份证打码功能测试
     *
     */
	public static void idCardTest(){
        try {
            // 通过阿里orc服务获取参数
            Point[] p =  new Point[4];
            p[0] = new Point(104,263);
            p[1] = new Point(913,272);
            p[2] = new Point(911,1530);
            p[3] = new Point(114,1527);
            //头像中心
            Point center = new Point(553,1252);

            Map<String,Double> map = new HashMap<String, Double>();

            map.put("width",(double)370);
            map.put("height",(double)450);


            File file = new File("C:\\Users\\PC0825\\Desktop\\zhaopian(1)\\4.jpg");
            File desensitizationFace = IdCardUtil.desensitization(file, p, center, ProportioEnum.face,map);
            File desensitizationId = IdCardUtil.desensitization(desensitizationFace, p, center, ProportioEnum.idNumber,map);
            File desensitizationName = IdCardUtil.desensitization(desensitizationId, p, center, ProportioEnum.name,map);
            File desensitization = IdCardUtil.desensitization(desensitizationName, p, center, ProportioEnum.address,map);


            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(desensitization));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("E:\\fillOval4.jpg"));
            byte[]bys = new byte[1024];
            int len;
            while ((len=bis.read(bys))!=-1){
                bos.write(bys,0,len);
            }
            bos.close();
            bis.close();
            desensitizationFace.deleteOnExit();
            desensitizationId.deleteOnExit();
            desensitizationName.deleteOnExit();
            desensitization.deleteOnExit();
        } catch (Exception e) {
            System.err.println("出错了！！！！");
            e.printStackTrace();
        }
    }

    /**
     * 获取字符串中的数字和小数点
     * @param pepo 目标字符串
     * @return
     */
    public static String numTest(String pepo){
//        String pepo = "120.25万人";
        Matcher matcher = compile.matcher(pepo);
        if (matcher.find()){
            String group = matcher.group();
            System.out.println("截取目标： "+ group);
            return group;
        }else {
            return "";
        }
    }

    /**
     * 通过经纬度获取距离(单位：千米)
     * @param lng1 经度1
     * @param lat1 纬度1
     * @param lng2 经度2
     * @param lat2 纬度2
     * @return 距离
     */
    public static double distanceTest(double lng1, double lat1, double lng2, double lat2){

        double radLng1 = lng1 * Math.PI / 180.0;
        double radLat1 = lat1 * Math.PI / 180.0;
        double radLng2 = lng2 * Math.PI / 180.0;
        double radLat2 = lat2 * Math.PI / 180.0;

        double a = radLat1 - radLat2;
        double b = radLng1 - radLng2;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        //赤道半径6378137
        s = s * radio/1000;
        System.out.println("两地距离为： "+ s +"Km");
        return s;
    }


}
