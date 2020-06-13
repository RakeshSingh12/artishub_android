package com.artishub.app.model;

import java.util.List;

/**
 * Created by ashutosh on 7/30/2018.
 */
public class AppConfigurationModel {

    /**
     * error_string : App Configurations
     * error_code : 0
     * payment_methods : [{"payment_method_id":"1","payment_method_name":"Mobile Money"},{"payment_method_id":"2","payment_method_name":"E-Wallet"},{"payment_method_id":"3","payment_method_name":"Bank Transfer"},{"payment_method_id":"4","payment_method_name":"Card"}]
     * delivery_mode : [{"delivery_mode_id":"1","delivery_mode_name":"Collect"},{"delivery_mode_id":"2","delivery_mode_name":"Wholesaler Delivery"},{"delivery_mode_id":"3","delivery_mode_name":"Logistics"}]
     * product_category : [{"category_id":"1","category_name":"Electronics"},{"category_id":"2","category_name":"Clothings"},{"category_id":"3","category_name":"Footwear"},{"category_id":"4","category_name":"Home and Furniture"},{"category_id":"5","category_name":"Toys"}]
     * product_subcategory : [{"subcategory_id":"1","subcategory_name":"Mobiles","subcategory_image":"http://52.27.53.102/artisthub/uploads/sub_categories/mobile.jpg","parent_category_id":"1"},{"subcategory_id":"2","subcategory_name":"Laptops","subcategory_image":"http://52.27.53.102/artisthub/uploads/sub_categories/laptop_1.png","parent_category_id":"1"},{"subcategory_id":"3","subcategory_name":"Womens Clothing","subcategory_image":"http://52.27.53.102/artisthub/uploads/sub_categories/women_fashion.png","parent_category_id":"2"},{"subcategory_id":"4","subcategory_name":"Mens Clothing","subcategory_image":"http://52.27.53.102/artisthub/uploads/sub_categories/men_fashion_1.png","parent_category_id":"2"},{"subcategory_id":"5","subcategory_name":"Kids Clothing","subcategory_image":"","parent_category_id":"2"},{"subcategory_id":"6","subcategory_name":"Heels","subcategory_image":"http://52.27.53.102/artisthub/uploads/sub_categories/heels_1.png","parent_category_id":"3"},{"subcategory_id":"7","subcategory_name":"Sneakers","subcategory_image":"http://52.27.53.102/artisthub/uploads/sub_categories/shoes_1.png","parent_category_id":"3"},{"subcategory_id":"8","subcategory_name":"Boots","subcategory_image":"http://52.27.53.102/artisthub/uploads/sub_categories/heels_2.png","parent_category_id":"3"},{"subcategory_id":"9","subcategory_name":"Casual Shoes","subcategory_image":"http://52.27.53.102/artisthub/uploads/sub_categories/shoes_1.png","parent_category_id":"3"},{"subcategory_id":"10","subcategory_name":"Formal Shoes","subcategory_image":"","parent_category_id":"3"},{"subcategory_id":"11","subcategory_name":"Flats","subcategory_image":"","parent_category_id":"3"},{"subcategory_id":"12","subcategory_name":"Kitchen Gadgets","subcategory_image":"http://52.27.53.102/artisthub/uploads/sub_categories/mixer.png","parent_category_id":"4"},{"subcategory_id":"13","subcategory_name":"Chairs","subcategory_image":"http://52.27.53.102/artisthub/uploads/sub_categories/chair.png","parent_category_id":"4"},{"subcategory_id":"14","subcategory_name":"Tables","subcategory_image":"","parent_category_id":"4"},{"subcategory_id":"15","subcategory_name":"Almirahs","subcategory_image":"http://52.27.53.102/artisthub/uploads/sub_categories/furniture.png","parent_category_id":"4"},{"subcategory_id":"16","subcategory_name":"Learning Toys","subcategory_image":"","parent_category_id":"5"},{"subcategory_id":"17","subcategory_name":"Robots","subcategory_image":"","parent_category_id":"5"},{"subcategory_id":"18","subcategory_name":"Camera","subcategory_image":"http://52.27.53.102/artisthub/uploads/sub_categories/camara.jpg","parent_category_id":"1"},{"subcategory_id":"19","subcategory_name":"tv","subcategory_image":"http://52.27.53.102/artisthub/uploads/sub_categories/tv.jpg","parent_category_id":"1"},{"subcategory_id":"20","subcategory_name":"headphone","subcategory_image":"http://52.27.53.102/artisthub/uploads/sub_categories/headphone.png","parent_category_id":"1"}]
     * banners : [{"banner_id":"1","banner_image":"http://52.27.53.102/artisthub/uploads/banners/bnr_1.png"},{"banner_id":"2","banner_image":"http://52.27.53.102/artisthub/uploads/banners/bnr_2.png"},{"banner_id":"3","banner_image":"http://52.27.53.102/artisthub/uploads/banners/bnr_3.png"},{"banner_id":"4","banner_image":"http://52.27.53.102/artisthub/uploads/banners/bnr_4.png"},{"banner_id":"5","banner_image":"http://52.27.53.102/artisthub/uploads/banners/bnr_5.png"}]
     */

    private String error_string;
    private int error_code;
    private List<PaymentMethodsBean> payment_methods;
    private List<DeliveryModeBean> delivery_mode;
    private List<ProductCategoryBean> product_category;
    private List<ProductSubcategoryBean> product_subcategory;
    private List<BannersBean> banners;

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

    public List<PaymentMethodsBean> getPayment_methods() {
        return payment_methods;
    }

    public void setPayment_methods(List<PaymentMethodsBean> payment_methods) {
        this.payment_methods = payment_methods;
    }

    public List<DeliveryModeBean> getDelivery_mode() {
        return delivery_mode;
    }

    public void setDelivery_mode(List<DeliveryModeBean> delivery_mode) {
        this.delivery_mode = delivery_mode;
    }

    public List<ProductCategoryBean> getProduct_category() {
        return product_category;
    }

    public void setProduct_category(List<ProductCategoryBean> product_category) {
        this.product_category = product_category;
    }

    public List<ProductSubcategoryBean> getProduct_subcategory() {
        return product_subcategory;
    }

    public void setProduct_subcategory(List<ProductSubcategoryBean> product_subcategory) {
        this.product_subcategory = product_subcategory;
    }

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public static class PaymentMethodsBean {
        /**
         * payment_method_id : 1
         * payment_method_name : Mobile Money
         */

        private String payment_method_id;
        private String payment_method_name;

        public String getPayment_method_id() {
            return payment_method_id;
        }

        public void setPayment_method_id(String payment_method_id) {
            this.payment_method_id = payment_method_id;
        }

        public String getPayment_method_name() {
            return payment_method_name;
        }

        public void setPayment_method_name(String payment_method_name) {
            this.payment_method_name = payment_method_name;
        }
    }

    public static class DeliveryModeBean {
        /**
         * delivery_mode_id : 1
         * delivery_mode_name : Collect
         */

        private String delivery_mode_id;
        private String delivery_mode_name;

        public String getDelivery_mode_id() {
            return delivery_mode_id;
        }

        public void setDelivery_mode_id(String delivery_mode_id) {
            this.delivery_mode_id = delivery_mode_id;
        }

        public String getDelivery_mode_name() {
            return delivery_mode_name;
        }

        public void setDelivery_mode_name(String delivery_mode_name) {
            this.delivery_mode_name = delivery_mode_name;
        }
    }

    public static class ProductCategoryBean {
        /**
         * category_id : 1
         * category_name : Electronics
         */

        private String category_id;
        private String category_name;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }
    }

    public static class ProductSubcategoryBean {
        /**
         * subcategory_id : 1
         * subcategory_name : Mobiles
         * subcategory_image : http://52.27.53.102/artisthub/uploads/sub_categories/mobile.jpg
         * parent_category_id : 1
         */

        private String subcategory_id;
        private String subcategory_name;
        private String subcategory_image;
        private String parent_category_id;

        public String getSubcategory_id() {
            return subcategory_id;
        }

        public void setSubcategory_id(String subcategory_id) {
            this.subcategory_id = subcategory_id;
        }

        public String getSubcategory_name() {
            return subcategory_name;
        }

        public void setSubcategory_name(String subcategory_name) {
            this.subcategory_name = subcategory_name;
        }

        public String getSubcategory_image() {
            return subcategory_image;
        }

        public void setSubcategory_image(String subcategory_image) {
            this.subcategory_image = subcategory_image;
        }

        public String getParent_category_id() {
            return parent_category_id;
        }

        public void setParent_category_id(String parent_category_id) {
            this.parent_category_id = parent_category_id;
        }
    }

    public static class BannersBean {
        /**
         * banner_id : 1
         * banner_image : http://52.27.53.102/artisthub/uploads/banners/bnr_1.png
         */

        private String banner_id;
        private String banner_image;

        public String getBanner_id() {
            return banner_id;
        }

        public void setBanner_id(String banner_id) {
            this.banner_id = banner_id;
        }

        public String getBanner_image() {
            return banner_image;
        }

        public void setBanner_image(String banner_image) {
            this.banner_image = banner_image;
        }
    }
}
