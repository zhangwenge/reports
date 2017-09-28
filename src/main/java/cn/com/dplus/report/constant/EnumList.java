package cn.com.dplus.report.constant;

/**
 * @作用:
 * @所在包: cn.com.dplus.report.constant
 * @开发者: 余浪
 * @邮箱: 365617581@qq.com
 * @时间: 2017/4/6
 * @公司: 广州讯动网络科技有限公司
 */
public class EnumList {

    public enum ModelType{
        RATION("定量",0),
        QUALITATIVE("定性",1);

        private final String name;
        private final Integer value;
        //构造器默认也只能是private, 从而保证构造函数只能在内部使用
        ModelType(String name,Integer value) {
            this.value = value;
            this.name = name;
        }
        public  Integer getValue() {
            return this.value;
        }
        public  String getName() {
            return this.name;
        }

        public static ModelType getModelType(Integer value){
            for(ModelType mt: values()){
                if(mt.getValue().equals(value)){
                    return mt;
                }
            }
            return null;
        }
    }

    public enum AttriType{
        TEXT("文本",0),
        NUM("数字",1),
        BOOL("布尔",2),
        ENUM("枚举",3),
        DATE("日期",4);

        private final String name;
        private final Integer value;
        //构造器默认也只能是private, 从而保证构造函数只能在内部使用
        AttriType(String name,Integer value) {
            this.value = value;
            this.name = name;
        }
        public  Integer getValue() {
            return this.value;
        }
        public  String getName() {
            return this.name;
        }
        public static AttriType getAttriType(int value){
            for(AttriType at: values()){
                if(at.getValue().equals(value)){
                    return at;
                }
            }
            return null;
        }
    }

}
