package com.artishub.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ashutosh on 9/13/2018.
 */
public class MyBagModel implements Serializable {


    /**
     * error_string : All Order Details
     * error_code : 0
     * result : [{"order_id":"ORDER_5552","payment_type":"","delivery_type":"1","supplier_id":"5","ordered":"2019-03-06 06:14:11","order_generated_id":"1","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"1","order_generated_id":"1","order_item_name":"Apple MacBook Pro Core i5 7th Gen - (8 GB/256 GB SSD/Mac OS Sierra) MPXU2HN/A  (13.3 inch, SIlver, 1","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551718767789832.1.jpeg","order_item_price":"299.00","order_item_total":"598.00","order_item_quantity":"2","seller":"Oliver","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 06:14:11","shipping_charge":"0","vat":"16"}],"total_amount":598,"count":1},{"order_id":"ORDER_8327","payment_type":"COD","delivery_type":"1","supplier_id":"4","ordered":"2019-03-06 06:17:25","order_generated_id":"3","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"2","order_generated_id":"3","order_item_name":"Apple MacBook Pro Core i5 7th Gen - (8 GB/256 GB SSD/Mac OS Sierra) MPXU2HN/A  (13.3 inch, SIlver, 1","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551718767789832.1.jpeg","order_item_price":"200.00","order_item_total":"400.00","order_item_quantity":"2","seller":"Tripti Bhardwaj","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 06:17:25","shipping_charge":"0","vat":"16"}],"total_amount":400,"count":1},{"order_id":"ORDER_3308","payment_type":"COD","delivery_type":"1","supplier_id":"5","ordered":"2019-03-06 06:17:25","order_generated_id":"3","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"3","order_generated_id":"3","order_item_name":"Apple MacBook Pro Core i5 7th Gen - (8 GB/256 GB SSD/Mac OS Sierra) MPXU2HN/A  (13.3 inch, SIlver, 1","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551718767789832.1.jpeg","order_item_price":"290.00","order_item_total":"290.00","order_item_quantity":"1","seller":"Oliver","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 06:17:25","shipping_charge":"0","vat":"16"}],"total_amount":290,"count":1},{"order_id":"ORDER_3126","payment_type":"","delivery_type":"1","supplier_id":"4","ordered":"2019-03-06 06:19:53","order_generated_id":"5","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"4","order_generated_id":"5","order_item_name":"Apple MacBook Pro Core i5 7th Gen - (8 GB/256 GB SSD/Mac OS Sierra) MPXU2HN/A  (13.3 inch, SIlver, 1","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551718767789832.1.jpeg","order_item_price":"200.00","order_item_total":"200.00","order_item_quantity":"1","seller":"Tripti Bhardwaj","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 06:19:53","shipping_charge":"0","vat":"16"}],"total_amount":200,"count":1},{"order_id":"ORDER_9763","payment_type":"","delivery_type":"1","supplier_id":"5","ordered":"2019-03-06 06:28:31","order_generated_id":"6","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"5","order_generated_id":"6","order_item_name":"Apple MacBook Pro Core i5 7th Gen - (8 GB/256 GB SSD/Mac OS Sierra) MPXU2HN/A  (13.3 inch, SIlver, 1","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551718767789832.1.jpeg","order_item_price":"290.00","order_item_total":"290.00","order_item_quantity":"1","seller":"Oliver","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 06:28:31","shipping_charge":"0","vat":"16"}],"total_amount":290,"count":1},{"order_id":"ORDER_2188","payment_type":"Wallet","delivery_type":"1","supplier_id":"5","ordered":"2019-03-06 06:44:29","order_generated_id":"9","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"7","order_generated_id":"9","order_item_name":"Apple MacBook Pro Core i5 7th Gen - (8 GB/256 GB SSD/Mac OS Sierra) MPXU2HN/A  (13.3 inch, SIlver, 1","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551718767789832.1.jpeg","order_item_price":"290.00","order_item_total":"290.00","order_item_quantity":"1","seller":"Oliver","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 06:44:29","shipping_charge":"0","vat":"16"}],"total_amount":290,"count":1},{"order_id":"ORDER_2716","payment_type":"Net Banking","delivery_type":"1","supplier_id":"4","ordered":"2019-03-06 06:45:11","order_generated_id":"10","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"8","order_generated_id":"10","order_item_name":"MSI GV Series Core i7 8th Gen - (16 GB/1 TB HDD/128 GB SSD/Windows 10 Home/6 GB Graphics) GV62 8RE-0","order_item_status":"Deliverd","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551853644154687.1.jpeg","order_item_price":"1990.00","order_item_total":"1990.00","order_item_quantity":"1","seller":"Tripti Bhardwaj","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 06:45:11","shipping_charge":"0","vat":"16"}],"total_amount":1990,"count":1},{"order_id":"ORDER_2380","payment_type":"COD","delivery_type":"1","supplier_id":"4","ordered":"2019-03-06 06:49:58","order_generated_id":"11","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"9","order_generated_id":"11","order_item_name":"MSI GV Series Core i7 8th Gen - (16 GB/1 TB HDD/128 GB SSD/Windows 10 Home/6 GB Graphics) GV62 8RE-0","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551853644154687.1.jpeg","order_item_price":"1990.00","order_item_total":"9950.00","order_item_quantity":"5","seller":"Tripti Bhardwaj","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 06:49:58","shipping_charge":"0","vat":"16"}],"total_amount":9950,"count":1},{"order_id":"ORDER_9553","payment_type":"COD","delivery_type":"1","supplier_id":"4","ordered":"2019-03-06 06:54:33","order_generated_id":"12","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"10","order_generated_id":"12","order_item_name":"MSI GV Series Core i7 8th Gen - (16 GB/1 TB HDD/128 GB SSD/Windows 10 Home/6 GB Graphics) GV62 8RE-0","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551853644154687.1.jpeg","order_item_price":"1990.00","order_item_total":"1990.00","order_item_quantity":"1","seller":"Tripti Bhardwaj","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 06:54:33","shipping_charge":"0","vat":"16"}],"total_amount":1990,"count":1},{"order_id":"ORDER_2015","payment_type":"COD","delivery_type":"1","supplier_id":"4","ordered":"2019-03-06 06:59:25","order_generated_id":"13","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"11","order_generated_id":"13","order_item_name":"MSI GV Series Core i7 8th Gen - (16 GB/1 TB HDD/128 GB SSD/Windows 10 Home/6 GB Graphics) GV62 8RE-0","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551853644154687.1.jpeg","order_item_price":"1990.00","order_item_total":"1990.00","order_item_quantity":"1","seller":"Tripti Bhardwaj","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 06:59:25","shipping_charge":"0","vat":"16"}],"total_amount":1990,"count":1},{"order_id":"ORDER_4069","payment_type":"COD","delivery_type":"1","supplier_id":"4","ordered":"2019-03-06 07:00:24","order_generated_id":"14","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"12","order_generated_id":"14","order_item_name":"MSI GV Series Core i7 8th Gen - (16 GB/1 TB HDD/128 GB SSD/Windows 10 Home/6 GB Graphics) GV62 8RE-0","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551853644154687.1.jpeg","order_item_price":"1990.00","order_item_total":"1990.00","order_item_quantity":"1","seller":"Tripti Bhardwaj","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 07:00:24","shipping_charge":"0","vat":"16"}],"total_amount":1990,"count":1},{"order_id":"ORDER_7132","payment_type":"COD","delivery_type":"1","supplier_id":"4","ordered":"2019-03-06 07:02:57","order_generated_id":"15","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"13","order_generated_id":"15","order_item_name":"MSI GV Series Core i7 8th Gen - (16 GB/1 TB HDD/128 GB SSD/Windows 10 Home/6 GB Graphics) GV62 8RE-0","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551853644154687.1.jpeg","order_item_price":"1990.00","order_item_total":"1990.00","order_item_quantity":"1","seller":"Tripti Bhardwaj","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 07:02:57","shipping_charge":"0","vat":"16"}],"total_amount":1990,"count":1},{"order_id":"ORDER_9245","payment_type":"COD","delivery_type":"1","supplier_id":"4","ordered":"2019-03-06 07:10:26","order_generated_id":"16","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"14","order_generated_id":"16","order_item_name":"MSI GV Series Core i7 8th Gen - (16 GB/1 TB HDD/128 GB SSD/Windows 10 Home/6 GB Graphics) GV62 8RE-0","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551853644154687.1.jpeg","order_item_price":"1990.00","order_item_total":"1990.00","order_item_quantity":"1","seller":"Tripti Bhardwaj","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 07:10:26","shipping_charge":"0","vat":"16"}],"total_amount":1990,"count":1},{"order_id":"ORDER_1491","payment_type":"COD","delivery_type":"1","supplier_id":"4","ordered":"2019-03-06 07:31:54","order_generated_id":"17","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"15","order_generated_id":"17","order_item_name":"MSI GV Series Core i7 8th Gen - (16 GB/1 TB HDD/128 GB SSD/Windows 10 Home/6 GB Graphics) GV62 8RE-0","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551853644154687.1.jpeg","order_item_price":"1990.00","order_item_total":"1990.00","order_item_quantity":"1","seller":"Tripti Bhardwaj","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 07:31:54","shipping_charge":"0","vat":"16"}],"total_amount":1990,"count":1},{"order_id":"ORDER_9740","payment_type":"COD","delivery_type":"1","supplier_id":"4","ordered":"2019-03-06 07:35:21","order_generated_id":"18","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"16","order_generated_id":"18","order_item_name":"MSI GV Series Core i7 8th Gen - (16 GB/1 TB HDD/128 GB SSD/Windows 10 Home/6 GB Graphics) GV62 8RE-0","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551853644154687.1.jpeg","order_item_price":"1990.00","order_item_total":"1990.00","order_item_quantity":"1","seller":"Tripti Bhardwaj","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 07:35:21","shipping_charge":"0","vat":"16"}],"total_amount":1990,"count":1},{"order_id":"ORDER_5722","payment_type":"COD","delivery_type":"1","supplier_id":"4","ordered":"2019-03-06 07:36:05","order_generated_id":"20","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"17","order_generated_id":"20","order_item_name":"MSI GV Series Core i7 8th Gen - (16 GB/1 TB HDD/128 GB SSD/Windows 10 Home/6 GB Graphics) GV62 8RE-0","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551853644154687.1.jpeg","order_item_price":"1990.00","order_item_total":"1990.00","order_item_quantity":"1","seller":"Tripti Bhardwaj","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 07:36:05","shipping_charge":"0","vat":"16"}],"total_amount":1990,"count":1},{"order_id":"ORDER_1083","payment_type":"COD","delivery_type":"1","supplier_id":"4","ordered":"2019-03-06 07:37:11","order_generated_id":"21","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"18","order_generated_id":"21","order_item_name":"MSI GV Series Core i7 8th Gen - (16 GB/1 TB HDD/128 GB SSD/Windows 10 Home/6 GB Graphics) GV62 8RE-0","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551853644154687.1.jpeg","order_item_price":"1990.00","order_item_total":"1990.00","order_item_quantity":"1","seller":"Tripti Bhardwaj","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 07:37:11","shipping_charge":"0","vat":"16"}],"total_amount":1990,"count":1},{"order_id":"ORDER_5783","payment_type":"COD","delivery_type":"1","supplier_id":"4","ordered":"2019-03-06 07:40:03","order_generated_id":"22","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"19","order_generated_id":"22","order_item_name":"MSI GV Series Core i7 8th Gen - (16 GB/1 TB HDD/128 GB SSD/Windows 10 Home/6 GB Graphics) GV62 8RE-0","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551853644154687.1.jpeg","order_item_price":"1990.00","order_item_total":"1990.00","order_item_quantity":"1","seller":"Tripti Bhardwaj","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 07:40:03","shipping_charge":"0","vat":"16"}],"total_amount":1990,"count":1},{"order_id":"ORDER_4104","payment_type":"COD","delivery_type":"1","supplier_id":"4","ordered":"2019-03-06 07:40:58","order_generated_id":"23","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"20","order_generated_id":"23","order_item_name":"MSI GV Series Core i7 8th Gen - (16 GB/1 TB HDD/128 GB SSD/Windows 10 Home/6 GB Graphics) GV62 8RE-0","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551853644154687.1.jpeg","order_item_price":"1990.00","order_item_total":"1990.00","order_item_quantity":"1","seller":"Tripti Bhardwaj","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 07:40:58","shipping_charge":"0","vat":"16"}],"total_amount":1990,"count":1},{"order_id":"ORDER_6816","payment_type":"COD","delivery_type":"1","supplier_id":"4","ordered":"2019-03-06 07:42:47","order_generated_id":"24","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"21","order_generated_id":"24","order_item_name":"MSI GV Series Core i7 8th Gen - (16 GB/1 TB HDD/128 GB SSD/Windows 10 Home/6 GB Graphics) GV62 8RE-0","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551853644154687.1.jpeg","order_item_price":"1990.00","order_item_total":"1990.00","order_item_quantity":"1","seller":"Tripti Bhardwaj","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 07:42:47","shipping_charge":"0","vat":"16"}],"total_amount":1990,"count":1},{"order_id":"ORDER_8601","payment_type":"COD","delivery_type":"1","supplier_id":"4","ordered":"2019-03-06 07:44:51","order_generated_id":"25","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"22","order_generated_id":"25","order_item_name":"MSI GV Series Core i7 8th Gen - (16 GB/1 TB HDD/128 GB SSD/Windows 10 Home/6 GB Graphics) GV62 8RE-0","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551853644154687.1.jpeg","order_item_price":"1990.00","order_item_total":"1990.00","order_item_quantity":"1","seller":"Tripti Bhardwaj","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 07:44:51","shipping_charge":"0","vat":"16"}],"total_amount":1990,"count":1},{"order_id":"ORDER_4265","payment_type":"Net Banking","delivery_type":"1","supplier_id":"5","ordered":"2019-03-06 11:47:33","order_generated_id":"26","delivered_date":"2019-03-11 00:00:00","items":[{"order_item_id":"23","order_generated_id":"26","order_item_name":"Apple MacBook Pro Core i5 7th Gen - (8 GB/256 GB SSD/Mac OS Sierra) MPXU2HN/A  (13.3 inch, SIlver, 1","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551718767789832.1.jpeg","order_item_price":"290.00","order_item_total":"580.00","order_item_quantity":"2","seller":"Oliver","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 11:47:33","shipping_charge":"0","vat":"16"}],"total_amount":580,"count":1}]
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

    public static class ResultBean implements Serializable {
        /**
         * order_id : ORDER_5552
         * payment_type :
         * delivery_type : 1
         * supplier_id : 5
         * ordered : 2019-03-06 06:14:11
         * order_generated_id : 1
         * delivered_date : 2019-03-11 00:00:00
         * items : [{"order_item_id":"1","order_generated_id":"1","order_item_name":"Apple MacBook Pro Core i5 7th Gen - (8 GB/256 GB SSD/Mac OS Sierra) MPXU2HN/A  (13.3 inch, SIlver, 1","order_item_status":"Placed","order_item_image":"http://52.27.53.102/artisthub/public/uploads/productImage/1551718767789832.1.jpeg","order_item_price":"299.00","order_item_total":"598.00","order_item_quantity":"2","seller":"Oliver","delivered_date":"2019-03-11 00:00:00","ordered":"2019-03-06 06:14:11","shipping_charge":"0","vat":"16"}]
         * total_amount : 598
         * count : 1
         */

        private String order_id;
        private String payment_type;
        private String delivery_type;
        private String supplier_id;
        private String ordered;
        private String order_generated_id;
        private String delivered_date;
        private int total_amount;
        private int count;
        private List<ItemsBean> items;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getPayment_type() {
            return payment_type;
        }

        public void setPayment_type(String payment_type) {
            this.payment_type = payment_type;
        }

        public String getDelivery_type() {
            return delivery_type;
        }

        public void setDelivery_type(String delivery_type) {
            this.delivery_type = delivery_type;
        }

        public String getSupplier_id() {
            return supplier_id;
        }

        public void setSupplier_id(String supplier_id) {
            this.supplier_id = supplier_id;
        }

        public String getOrdered() {
            return ordered;
        }

        public void setOrdered(String ordered) {
            this.ordered = ordered;
        }

        public String getOrder_generated_id() {
            return order_generated_id;
        }

        public void setOrder_generated_id(String order_generated_id) {
            this.order_generated_id = order_generated_id;
        }

        public String getDelivered_date() {
            return delivered_date;
        }

        public void setDelivered_date(String delivered_date) {
            this.delivered_date = delivered_date;
        }

        public int getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(int total_amount) {
            this.total_amount = total_amount;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean implements Serializable {
            /**
             * order_item_id : 1
             * order_generated_id : 1
             * order_item_name : Apple MacBook Pro Core i5 7th Gen - (8 GB/256 GB SSD/Mac OS Sierra) MPXU2HN/A  (13.3 inch, SIlver, 1
             * order_item_status : Placed
             * order_item_image : http://52.27.53.102/artisthub/public/uploads/productImage/1551718767789832.1.jpeg
             * order_item_price : 299.00
             * order_item_total : 598.00
             * order_item_quantity : 2
             * seller : Oliver
             * delivered_date : 2019-03-11 00:00:00
             * ordered : 2019-03-06 06:14:11
             * shipping_charge : 0
             * vat : 16
             */

            private String order_item_id;
            private String order_generated_id;
            private String order_item_name;
            private String order_item_status;
            private String order_item_image;
            private String order_item_price;
            private String order_item_total;
            private String order_item_quantity;
            private String seller;
            private String delivered_date;
            private String ordered;
            private String shipping_charge;
            private String vat;

            public String getOrder_item_id() {
                return order_item_id;
            }

            public void setOrder_item_id(String order_item_id) {
                this.order_item_id = order_item_id;
            }

            public String getOrder_generated_id() {
                return order_generated_id;
            }

            public void setOrder_generated_id(String order_generated_id) {
                this.order_generated_id = order_generated_id;
            }

            public String getOrder_item_name() {
                return order_item_name;
            }

            public void setOrder_item_name(String order_item_name) {
                this.order_item_name = order_item_name;
            }

            public String getOrder_item_status() {
                return order_item_status;
            }

            public void setOrder_item_status(String order_item_status) {
                this.order_item_status = order_item_status;
            }

            public String getOrder_item_image() {
                return order_item_image;
            }

            public void setOrder_item_image(String order_item_image) {
                this.order_item_image = order_item_image;
            }

            public String getOrder_item_price() {
                return order_item_price;
            }

            public void setOrder_item_price(String order_item_price) {
                this.order_item_price = order_item_price;
            }

            public String getOrder_item_total() {
                return order_item_total;
            }

            public void setOrder_item_total(String order_item_total) {
                this.order_item_total = order_item_total;
            }

            public String getOrder_item_quantity() {
                return order_item_quantity;
            }

            public void setOrder_item_quantity(String order_item_quantity) {
                this.order_item_quantity = order_item_quantity;
            }

            public String getSeller() {
                return seller;
            }

            public void setSeller(String seller) {
                this.seller = seller;
            }

            public String getDelivered_date() {
                return delivered_date;
            }

            public void setDelivered_date(String delivered_date) {
                this.delivered_date = delivered_date;
            }

            public String getOrdered() {
                return ordered;
            }

            public void setOrdered(String ordered) {
                this.ordered = ordered;
            }

            public String getShipping_charge() {
                return shipping_charge;
            }

            public void setShipping_charge(String shipping_charge) {
                this.shipping_charge = shipping_charge;
            }

            public String getVat() {
                return vat;
            }

            public void setVat(String vat) {
                this.vat = vat;
            }
        }
    }
}
