package com.scm.controllers;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

  @Value("${razorpay.key.id}")
  private String keyId;

  @Value("${razorpay.key.secret}")
  private String keySecret;

  // Step 1: Frontend calls this to create an order
  @PostMapping("/create-order")
  public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Map<String, Object> data) {
    Map<String, Object> response = new HashMap<>();
    try {
      int amount = (int) data.get("amount"); // amount in paise (₹99 = 9900)

      RazorpayClient client = new RazorpayClient(keyId, keySecret);

      JSONObject options = new JSONObject();
      options.put("amount", amount);
      options.put("currency", "INR");
      options.put("receipt", "txn_" + System.currentTimeMillis());
      options.put("payment_capture", 1); // auto capture

      Order order = client.orders.create(options);

      response.put("success", true);
      response.put("orderId", order.get("id"));
      response.put("amount", amount);
      response.put("currency", "INR");
      response.put("keyId", keyId);

    } catch (RazorpayException e) {
      response.put("success", false);
      response.put("error", e.getMessage());
    }
    return ResponseEntity.ok(response);
  }

  // Step 2: Frontend calls this after payment to verify signature
  @PostMapping("/verify")
  public ResponseEntity<Map<String, Object>> verifyPayment(@RequestBody Map<String, String> data) {
    Map<String, Object> response = new HashMap<>();
    try {
      String orderId = data.get("razorpay_order_id");
      String paymentId = data.get("razorpay_payment_id");
      String signature = data.get("razorpay_signature");

      // Verify HMAC SHA256 signature
      String payload = orderId + "|" + paymentId;
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(new SecretKeySpec(keySecret.getBytes(), "HmacSHA256"));
      byte[] hash = mac.doFinal(payload.getBytes());

      // Convert to hex
      StringBuilder hexHash = new StringBuilder();
      for (byte b : hash)
        hexHash.append(String.format("%02x", b));

      if (hexHash.toString().equals(signature)) {
        // ✅ Payment verified — money is in your Razorpay account
        response.put("success", true);
        response.put("message", "Payment verified successfully!");
        response.put("paymentId", paymentId);
      } else {
        // ❌ Signature mismatch — possible fraud
        response.put("success", false);
        response.put("message", "Payment verification failed.");
      }

    } catch (Exception e) {
      response.put("success", false);
      response.put("message", "Error: " + e.getMessage());
    }
    return ResponseEntity.ok(response);
  }
}