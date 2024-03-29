package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText)findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage= createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param addChocolate  is whether or not the user wants chocolate topping.
     * @param addWhippedCream is whether or not the user wants whipped cream topping.
     *@return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        //Price of 1 cup of coffee
        int basePrice =5;

        //Add $1 if the user wants whipped cream topping.
        if(addWhippedCream){
            basePrice = basePrice +1;
        }

        //Add $2 if the user wants chocolate topping.
        if(addChocolate){
            basePrice = basePrice +2;
        }

        //Calculate the total order price.
        return  quantity * basePrice;
    }

    int quantity=0;
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view){
        if (quantity==100){
            //Show an error message as a toast
            Toast.makeText(this,"You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            //Exit this method early there's nothing left to do.
            return;
        }
        quantity=quantity+1;
        displayQuantity (quantity);
    }

    /**
     * Create summary of the order
     *
     * @param name enter your name
     * @param price  of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @return text summary
     */


    public String createOrderSummary (String name, int price, boolean addWhippedCream, boolean addChocolate){
        String priceMessage=  getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream,addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;

    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view){
        if (quantity==1){
            //Show an error message as a toast
            Toast.makeText(this,"You cannot have less than one coffee", Toast.LENGTH_SHORT).show();
            //Exit this method early there's nothing left to do.
            return;
        }
        quantity=quantity-1;
        displayQuantity (quantity);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}