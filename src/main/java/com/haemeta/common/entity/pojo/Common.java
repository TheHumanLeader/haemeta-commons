package com.haemeta.common.entity.pojo;

public class Common {
    public interface Login{
        default void login() throws Exception{
            System.out.println("登录");
        }
    }
    public interface Logout{
        default void logout() throws Exception{
            System.out.println("登出");
        }
    }
    public interface Pay<T>{
        default void pay(T order) throws Exception{
            System.out.println("支付了订单");
        }
    }
    public enum OrderType implements Pay<OrderPojo>{
        A("直销"){
            @Override
            public void pay(OrderPojo order) throws Exception {
                System.out.println("支付直销订单");
            }
        },
        B("分销"){
            @Override
            public void pay(OrderPojo order) throws Exception {
                System.out.println("支付分销订单");
                //TODO 分销提成计算
            }
        }

        ;
        private String description;

        OrderType(String description) {
            this.description = description;
        }

        public static OrderType getInstance(Integer code){
            switch (code){
                case 0:
                    return A;
                case 1:
                    return B;
                default:
                    return null;
            }
        }
    }
    public static class OrderPojo{
        private Integer orderType;

        public OrderPojo(Integer orderType) {
            this.orderType = orderType;
        }

        private OrderType orderType(){
            return OrderType.getInstance(orderType);
        }
    }
    public static class UserVo{}
    public static class UserPojo implements Login,Logout,Pay<OrderPojo>{
        protected boolean login;
        public UserPojo(UserVo vo){}
        public UserPojo(UserDto vo){}
        UserVo parseVo(){
            return new UserVo();
        }
        UserDto parseDto(){
            return new UserDto();
        }
    }
    public static class UserDto{}
    public static void main(String[] args) throws Exception {
        UserPojo userPojo = new UserPojo(new UserDto()){
            @Override
            public void login() throws Exception {
                this.login = true;
            }

            @Override
            public void logout() throws Exception {
                this.login = false;
            }

            @Override
            public void pay(OrderPojo order) throws Exception {
                //

                if(login)
                    order.orderType().pay(order);
                else
                    System.out.println("用户未登录");
            }
        };
        userPojo.login();
        userPojo.logout();
        userPojo.pay(new OrderPojo(1));
    }
}
