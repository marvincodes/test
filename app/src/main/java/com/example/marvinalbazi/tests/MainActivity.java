package com.example.marvinalbazi.tests;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


    public class MainActivity extends AppCompatActivity {
        int quantity = 1;
        int pricePerCup = 5;
        String priceMessage;
        boolean hasWhippedCream;
        boolean hasChocolate;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }

        // This method is called when the plus Button is clicked.
        public void quantityUp(View view) {
            if (quantity == 100){
                Toast.makeText(getApplicationContext(),"Maximum coffee order is 100",Toast.LENGTH_LONG).show();
                return;
            }
            quantity = quantity + 1;
            displayQuantity(quantity);
        }

        // This method is called when the minus button is clicked.
        public void quantityDown(View view) {
            if (quantity == 1) {
                Toast.makeText(getApplicationContext(),"Minimum coffee order is 1",Toast.LENGTH_LONG).show();
                return;
            }
            quantity = quantity - 1;
            displayQuantity(quantity);
        }


        // This method is called when the order button is clicked.
        public void submitOrder(View view) {
            
                EditText name = (EditText) findViewById(R.id.et_name);
                String enterName = name.getText().toString();
                CheckBox chocolateBox = (CheckBox) findViewById(R.id.check_box_chocolate);
                boolean hasChocolate = chocolateBox.isChecked();
                CheckBox whippedCreamBox = (CheckBox) findViewById(R.id.check_box_whipped_cream);
                hasWhippedCream = whippedCreamBox.isChecked();
                priceMessage = createOrderSummary(calculatePrice(), hasWhippedCream, hasChocolate, enterName);
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto: marvin_al-bazi@hotmail.com")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order Summary for " + enterName);
                intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                //displayMessage(priceMessage);
            }
        }
         //This method displays the price message
//       private void displayMessage(String message) {
//            TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//            orderSummaryTextView.setText(message);
//        }

        // This method displays the given quantity value on the screen.
        public void displayQuantity(int number) {
            TextView quantityNum = (TextView) findViewById(R.id.tv_num);
            quantityNum.setText("" + number);
        }

        // This method calculates the price.
        private int calculatePrice() {
            if (hasWhippedCream && hasChocolate) {
                return (pricePerCup + 3) * quantity;
            } else if (hasChocolate) {
                return (pricePerCup + 2) * quantity;
            } else if (hasWhippedCream) {
                return (pricePerCup + 1) * quantity;
            } else {
                return quantity * pricePerCup;
            }
        }


        // This method creates an order summary of purchase
        private String createOrderSummary (int price, boolean addWhippedCream, boolean addChocolate, String name){
            String priceMessage = "Name " + name;
            priceMessage += "\nAdd Whipped cream? " + addWhippedCream;
            priceMessage += "\nAdd Chocolate? " + addChocolate;
            priceMessage += "\nQuantity: " + quantity;
            priceMessage += "\nAmount due £" + price;
            priceMessage +="\nThank you";
            return priceMessage;
        }


    }
