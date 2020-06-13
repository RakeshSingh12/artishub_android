package com.artishub.app.model;

import java.util.List;

/**
 * Created by ashutosh on 8/7/2018.
 */
public class ProductDetailsModel {
    /**
     * error_string : Product Details
     * error_code : 0
     * product_details : {"product_id":"1","product_barcode":"asfdfsd12dascd","product_name":"test product","product_description":"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.","unit_price":"100","selling_price":"500","unit_in_stock":"15","units_on_order":"10","reorder_level":"10","subcategory":"Mobiles","category":"Electronics","supplier_id":"Rishabh Anand","product_images":["http://52.27.53.102/artisthub/uploads/sub_categories/mobile.jpg","http://52.27.53.102/artisthub/uploads/sub_categories/smart_phone_2.png","http://52.27.53.102/artisthub/uploads/sub_categories/mobile.jpg","http://52.27.53.102/artisthub/uploads/sub_categories/smart_phone_2.png"]}
     */

    private String error_string;
    private int error_code;
    private ProductDetailsBean product_details;

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

    public ProductDetailsBean getProduct_details() {
        return product_details;
    }

    public void setProduct_details(ProductDetailsBean product_details) {
        this.product_details = product_details;
    }

    public static class ProductDetailsBean {
        /**
         * product_id : 1
         * product_barcode : asfdfsd12dascd
         * product_name : test product
         * product_description : Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
         * unit_price : 100
         * selling_price : 500
         * unit_in_stock : 15
         * units_on_order : 10
         * reorder_level : 10
         * subcategory : Mobiles
         * category : Electronics
         * supplier_id : Rishabh Anand
         * product_images : ["http://52.27.53.102/artisthub/uploads/sub_categories/mobile.jpg","http://52.27.53.102/artisthub/uploads/sub_categories/smart_phone_2.png","http://52.27.53.102/artisthub/uploads/sub_categories/mobile.jpg","http://52.27.53.102/artisthub/uploads/sub_categories/smart_phone_2.png"]
         */

        private String product_id;
        private String product_barcode;
        private String product_name;
        private String product_description;
        private String unit_price;
        private String selling_price;
        private String unit_in_stock;
        private String units_on_order;
        private String reorder_level;
        private String subcategory;
        private String category;
        private String supplier_id;
        private List<String> product_images;

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getProduct_barcode() {
            return product_barcode;
        }

        public void setProduct_barcode(String product_barcode) {
            this.product_barcode = product_barcode;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_description() {
            return product_description;
        }

        public void setProduct_description(String product_description) {
            this.product_description = product_description;
        }

        public String getUnit_price() {
            return unit_price;
        }

        public void setUnit_price(String unit_price) {
            this.unit_price = unit_price;
        }

        public String getSelling_price() {
            return selling_price;
        }

        public void setSelling_price(String selling_price) {
            this.selling_price = selling_price;
        }

        public String getUnit_in_stock() {
            return unit_in_stock;
        }

        public void setUnit_in_stock(String unit_in_stock) {
            this.unit_in_stock = unit_in_stock;
        }

        public String getUnits_on_order() {
            return units_on_order;
        }

        public void setUnits_on_order(String units_on_order) {
            this.units_on_order = units_on_order;
        }

        public String getReorder_level() {
            return reorder_level;
        }

        public void setReorder_level(String reorder_level) {
            this.reorder_level = reorder_level;
        }

        public String getSubcategory() {
            return subcategory;
        }

        public void setSubcategory(String subcategory) {
            this.subcategory = subcategory;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getSupplier_id() {
            return supplier_id;
        }

        public void setSupplier_id(String supplier_id) {
            this.supplier_id = supplier_id;
        }

        public List<String> getProduct_images() {
            return product_images;
        }

        public void setProduct_images(List<String> product_images) {
            this.product_images = product_images;
        }
    }
}
