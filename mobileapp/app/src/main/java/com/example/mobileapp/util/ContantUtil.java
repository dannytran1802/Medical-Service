package com.example.mobileapp.util;

import com.example.mobileapp.dto.AuthDTO;
import com.example.mobileapp.dto.BookingDTO;
import com.example.mobileapp.dto.MessageDTO;
import com.example.mobileapp.dto.RegisterDTO;
import com.example.mobileapp.model.Orders;
import com.example.mobileapp.model.Pharmacy;
import com.example.mobileapp.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContantUtil {

    public static RegisterDTO registerDTO = null;
    public static BookingDTO bookingDTO = null;
    public static AuthDTO authDTO = null;
    public static String roleName = null;

    // message
    public static HashMap<String, MessageDTO> messageList = null;

    // latitude
    public static double latitude = 10.848502;
    public static double longitude = 106.7735599;

    public static long pharmacyId = 0;
    public static Pharmacy pharmacy = null;

    // order
    public static Orders orders = null;

    // token
    public static String accessToken = null;
    public static String refreshToken = null;

    // cart
    public static Map<Long, Product> hashMapCart = new HashMap();

    public static void clearToCart() {
        hashMapCart = new HashMap();
    }

    public static void addToCart(long productId, Product product) {
        if (hashMapCart == null) {
            hashMapCart = new HashMap();
        }

        if (hashMapCart.containsKey(productId)) {
            Product p = hashMapCart.get(productId);
            int qty = p.getQty();
            p.setQty(qty + 1);
            hashMapCart.put(productId, p);
        } else {
            product.setQty(1);
            hashMapCart.put(productId, product);
        }
    }

    public static void removeToCart(long productId) {
        if (hashMapCart == null) {
            hashMapCart = new HashMap();
        }

        if (hashMapCart.containsKey(productId)) {
            Product p = hashMapCart.get(productId);
            int qty = p.getQty();

            if (qty > 1) {
                p.setQty(qty - 1);
                hashMapCart.put(productId, p);
            } else {
                p.setQty(0);
                hashMapCart.put(productId, p);
            }
        }
    }

    public static void deleteToCart(long productId) {
        if (hashMapCart == null) {
            hashMapCart = new HashMap();
        }

        if (hashMapCart.containsKey(productId)) {
            Product p = hashMapCart.get(productId);
            p.setQty(0);
            hashMapCart.put(productId, p);
        }
    }

    public static List<Product> getCart() {
        List<Product> productList = new ArrayList<>();
        if (hashMapCart != null) {
            for (Map.Entry<Long, Product> entry : hashMapCart.entrySet()) {
                productList.add(entry.getValue());
            }
        }

        return productList;
    }

    public static List<Product> getCartActive() {
        List<Product> productList = new ArrayList<>();
        if (hashMapCart != null) {
            for (Map.Entry<Long, Product> entry : hashMapCart.entrySet()) {
                if (entry.getValue().getQty() > 0) {
                    productList.add(entry.getValue());
                }
            }
        }

        return productList;
    }

    public static Product getProduct(long productId) {
        Product product = null;
        if (hashMapCart != null) {
            product = hashMapCart.get(productId);
        }

        return product;
    }

    public static boolean checkMessage(String key) {
        if (messageList != null) {
            if (messageList.containsKey(key)) {
                return true;
            }
        }

        return false;
    }

    public static void addMessage(MessageDTO message) {
        if (messageList == null) {
            messageList = new HashMap<>();
        }

        if (!messageList.containsKey(message.getId())) {
            messageList.put(message.getId(), message);
        }
    }

    public static List<MessageDTO> getMessage() {
        List<MessageDTO> messageDTOList = new ArrayList<>();

        if (messageList != null) {
            for (Map.Entry<String, MessageDTO> entry : messageList.entrySet()) {
                messageDTOList.add(entry.getValue());
            }
        }

        return messageDTOList;
    }

}
