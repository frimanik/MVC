package com.example.MVC_ObjectMapper;

public class TestJson {
    static final String json ="{\n" +
            "  \"orderId\": 0,\n" +
            "  \"customer\":{\n" +
            "      \"customerId\":1,\n" +
            "       \"firstName\":\"bob\",\n" +
            "       \"lastName\":\"bob\",\n" +
            "       \"contactNumber\":\"56345213\"},\n" +
            "  \"products\":[{\n" +
            "        \"productId\": 1,\n" +
            "        \"name\": \"lamp\",\n" +
            "        \"description\": \"nice lamp\",\n" +
            "        \"price\": 5,\n" +
            "        \"quantityInStock\": 100\n" +
            "  }, \n" +
            "   { \n" +
            "        \"productId\": 2,\n" +
            "        \"name\": \"sofa\",\n" +
            "        \"description\": \"yeah sofa\",\n" +
            "        \"price\": 7,\n" +
            "        \"quantityInStock\": 100\n" +
            "    },\n" +
            "    {\n" +
            "        \"productId\": 3,\n" +
            "        \"name\": \"fridge\",\n" +
            "        \"description\": \"pretty cool\",\n" +
            "        \"price\": 5,\n" +
            "        \"quantityInStock\": 100\n" +
            "    }],\n" +
            "  \"shippingAddress\": \"Over There\",\n" +
            "  \"totalPrice\": 120,\n" +
            "  \"status\": \"Awaiting\"\n" +
            "}\n}";
}
