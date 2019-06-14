package test.cj.com.myapplication;

/**
 * Created by Administrator on 2019/4/1.
 */

public class CheckPoint {
    private String id;
    private String projectID;
    private String code;
    private String checkTime;//检测时间
    private String testTime;//试验时间
    private String address;//检验地点
    private String lyaer;//试验土层
    private String part;//工程部位
    private String elevation;//标高
    private String gist;//试验依据
    private String minDensity;//要求最小干密度
    private String minVolume;//要求最小固体体积率
    private String type;//试验方法

    private String stz;//湿土重
    private String sktj;//试坑体积
    private String smd;//湿密度
    private String hsl;//含水率
    private String gmd;//干密度
    private String gttjl;//固体体积率
    private String bz;//比重

    private String relateID;//关联id
    private String relateCode;//关联code

    private String state;//上传状态 1、未上传 2、已上传
    private String remark;//备注
    private String createTime;//创建时间
    private String updateTime;//修改时间
    private String uploadTime;//上传时间
    private String mapLatitude;//纬度
    private String mapLongitude;//经度
    private String mapTime;//gps时间

}
