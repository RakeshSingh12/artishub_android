package com.artishub.app.model;

import java.util.List;

/**
 * Created by ashutosh on 8/8/2018.
 */
public class SearchDetailsModel {

    /**
     * error_string : Product Details
     * error_code : 0
     * product_details : [{"product_id":"10","product_barcode":"dfasdfdadfmaskldfj287489jkdjhf","product_image":"http://52.27.53.102/artisthub/uploads/sub_categories/laptop_1.png","product_name":"Asus Laptop","product_description":"","unit_price":"15000","selling_price":"18000","unit_in_stock":"200","units_on_order":"100","reorder_level":"10","supplier_id":"Rishabh Anand"}]
     */

    private String error_string;
    private int error_code;
    private List<ProductDetailsBean> product_details;

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

    public List<ProductDetailsBean> getProduct_details() {
        return product_details;
    }

    public void setProduct_details(List<ProductDetailsBean> product_details) {
        this.product_details = product_details;
    }

    public static class ProductDetailsBean {
        /**
         * product_id : 10
         * product_barcode : dfasdfdadfmaskldfj287489jkdjhf
         * product_image : http://52.27.53.102/artisthub/uploads/sub_categories/laptop_1.png
         * product_name : Asus Laptop
         * product_description :
         * unit_price : 15000
         * selling_price : 18000
         * unit_in_stock : 200
         * units_on_order : 100
         * reorder_level : 10
         * supplier_id : Rishabh Anand
         */

        private String product_id;
        private String product_barcode;
        private String product_image;
        private String product_name;
        private String product_description;
        private String unit_price;
        private String selling_price;
        private String unit_in_stock;
        private String units_on_order;
        private String reorder_level;
        private String supplier_id;

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

        public String getProduct_image() {
            return product_image;
        }

        public void setProduct_image(String product_image) {
            this.product_image = product_image;
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

        public String getSupplier_id() {
            return supplier_id;
        }

        public void setSupplier_id(String supplier_id) {
            this.supplier_id = supplier_id;
        }
    }
}
