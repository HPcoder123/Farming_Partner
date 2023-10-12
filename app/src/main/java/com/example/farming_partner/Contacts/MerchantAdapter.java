package com.example.farming_partner.Contacts;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.farming_partner.merchants;
import com.example.farming_partner.R;
import java.util.List;

public class MerchantAdapter extends RecyclerView.Adapter<MerchantAdapter.MerchantViewHolder> {

    private Context context;
    private List<merchants> merchantList;

    public MerchantAdapter(Context context, List<merchants> merchantList) {
        this.context = context;
        this.merchantList = merchantList;
    }

    @NonNull
    @Override
    public MerchantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_merchant, parent, false);
        return new MerchantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MerchantViewHolder holder, int position) {
        merchants merchant = merchantList.get(position);

        holder.textViewName.setText("Name: " + merchant.getName());
        holder.textViewCity.setText("City: " + merchant.getCity());
        holder.textViewContact.setText("Contact: " + merchant.getContact());

        // Set the phone number in the hidden TextView
        holder.textViewPhoneNumber.setText(merchant.getContact());

        // Load and display the profile picture if available
        if (!TextUtils.isEmpty(merchant.getprofileImageBase64())) {
            byte[] decodedString = Base64.decode(merchant.getprofileImageBase64(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.imageViewProfilePicture.setImageBitmap(decodedByte);
        } else {
            // Set the default image if profilePicture is empty
            holder.imageViewProfilePicture.setImageResource(R.drawable.default_profile_image);
        }

        // Set a click listener for the "Call" button
        holder.buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = holder.textViewPhoneNumber.getText().toString();
                initiatePhoneCall(context, phoneNumber);
            }
        });
    }

    @Override
    public int getItemCount() {
        return merchantList.size();
    }

    static class MerchantViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewCity, textViewContact, textViewPhoneNumber;
        ImageView imageViewProfilePicture;
        ImageButton buttonCall;

        MerchantViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewCity = itemView.findViewById(R.id.textViewCity);
            textViewContact = itemView.findViewById(R.id.textViewContact);
            textViewPhoneNumber = itemView.findViewById(R.id.textViewPhoneNumber);
            imageViewProfilePicture = itemView.findViewById(R.id.imageViewProfilePicture);
            buttonCall = itemView.findViewById(R.id.buttonCall);
        }
    }

    private void initiatePhoneCall(Context context, String phoneNumber) {
        // Create an intent to initiate a phone call
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));

        try {
            // Start the phone call intent
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Handle exceptions if the device doesn't support phone calls or the dialer app is not available
            Toast.makeText(context, "Unable to make a call", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}


