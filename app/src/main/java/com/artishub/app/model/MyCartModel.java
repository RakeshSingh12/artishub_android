package com.artishub.app.model;

import java.util.List;

/**
 * Created by ashutosh on 9/6/2018.
 */
public class MyCartModel {
    /**
     * error_string : All Cart Details
     * error_code : 0
     * result : [{"cart_id":"5","product_id":"1","product_name":"Casual Shoe","product_image":"http://52.27.53.102/artisthub/public/uploads/productImage/153743908528563shoes_1.png","product_unit_price":"850.00","product_quantity":"1","total_amount":"850.00","supplier_id":"Ashutosh","shipping_charges":"50","vat":16}]
     */

    private String error_string;
    private int error_code;
    private List<ResultBean> result;

    public String getError_string() {
        return error_string;
    }

    public void setError_string(String error_string) {
        this.error_string = error_string;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * cart_id : 5
         * product_id : 1
         * product_name : Casual Shoe
         * product_image : http://52.27.53.102/artisthub/public/uploads/productImage/153743908528563shoes_1.png
         * product_unit_price : 850.00
         * product_quantity : 1
         * total_amount : 850.00
         * supplier_id : Ashutosh
         * shipping_charges : 50
         * vat : 16
         */

        private String cart_id;
        private String product_id;
        private String product_name;
        private String product_image;
        private String product_unit_price;
        private String product_quantity;
        private String total_amount;
        private String supplier_id;
        private String shipping_charges;
        private int vat;

        public String getCart_id() {
            return cart_id;
        }

        public void setCart_id(String cart_id) {
            this.cart_id = cart_id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_image() {
            return product_image;
        }

        public void setProduct_image(String product_image) {
            this.product_image = product_image;
        }

        public String getProduct_unit_price() {
            return product_unit_price;
        }

        public void setProduct_unit_price(String product_unit_price) {
            this.product_unit_price = product_unit_price;
        }

        public String getProduct_quantity() {
            return product_quantity;
        }

        public void setProduct_quantity(String product_quantity) {
            this.product_quantity = product_quantity;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getSupplier_id() {
            return supplier_id;
        }

        public void setSupplier_id(String supplier_id) {
            this.supplier_id = supplier_id;
        }

        public String getShipping_charges() {
            return shipping_charges;
        }

        public void setShipping_charges(String shipping_charges) {
            this.shipping_charges = shipping_charges;
        }

        public int getVat() {
            return vat;
        }

        public void setVat(int vat) {
            this.vat = vat;
        }
    }
//    /**
//     * error_string : All Cart Details
//     * error_code : 0
//     * result : [{"cart_id":"2","product_id":"1","product_name":"test product","product_image":"http://52.27.53.102/artisthub/uploads/sub_categories/mobile.jpg","product_unit_price":"100.00","product_quantity":"1","total_amount":"100.00","supplier_id":"Rishabh Anand"}]
//     */
//
//    private String error_string;
//    private int error_code;
//    private List<ResultBean> result;
//
//    public String getError_string() {
//        return error_string;
//    }
//
//    public void setError_string(String error_string) {
//        this.error_string = error_string;
//    }
//
//    public int getError_code() {
//        return error_code;
//    }
//
//    public void setError_code(int error_code) {
//        this.error_code = error_code;
//    }
//
//    public List<ResultBean> getResult() {
//        return result;
//    }
//
//    public void setResult(List<ResultBean> result) {
//        this.result = result;
//    }
//
//    public static class ResultBean {
//        /**
//         * cart_id : 2
//         * product_id : 1
//         * product_name : test product
//         * product_image : http://52.27.53.102/artisthub/uploads/sub_categories/mobile.jpg
//         * product_unit_price : 100.00
//         * product_quantity : 1
//         * total_amount : 100.00
//         * supplier_id : Rishabh Anand
//         */
//
//        private String cart_id;
//        private String product_id;
//        private String product_name;
//        private String product_image;
//        private String product_unit_price;
//        private String product_quantity;
//        private String total_amount;
//        private String supplier_id;
//
//        public String getCart_id() {
//            return cart_id;
//        }
//
//        public void setCart_id(String cart_id) {
//            this.cart_id = cart_id;
//        }
//
//        public String getProduct_id() {
//            return product_id;
//        }
//
//        public void setProduct_id(String product_id) {
//            this.product_id = product_id;
//        }
//
//        public String getProduct_name() {
//            return product_name;
//        }
//
//        public void setProduct_name(String product_name) {
//            this.product_name = product_name;
//        }
//
//        public String getProduct_image() {
//            return product_image;
//        }
//
//        public void setProduct_image(String product_image) {
//            this.product_image = product_image;
//        }
//
//        public String getProduct_unit_price() {
//            return product_unit_price;
//        }
//
//        public void setProduct_unit_price(String product_unit_price) {
//            this.product_unit_price = product_unit_price;
//        }
//
//        public String getProduct_quantity() {
//            return product_quantity;
//        }
//
//        public void setProduct_quantity(String product_quantity) {
//            this.product_quantity = product_quantity;
//        }
//
//        public String getTotal_amount() {
//            return total_amount;
//        }
//
//        public void setTotal_amount(String total_amount) {
//            this.total_amount = total_amount;
//        }
//
//        public String getSupplier_id() {
//            return supplier_id;
//        }
//
//        public void setSupplier_id(String supplier_id) {
//            this.supplier_id = supplier_id;
//        }
//    }


}
