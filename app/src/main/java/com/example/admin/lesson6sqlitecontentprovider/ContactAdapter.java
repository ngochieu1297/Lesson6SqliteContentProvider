package com.example.admin.lesson6sqlitecontentprovider;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private List<Contact> mContacts;
    private OnClickContactItem mListener;
    private LayoutInflater mInflater;

    public ContactAdapter(Context context, List<Contact> contacts, OnClickContactItem listener) {
        mContacts = contacts;
        mListener = listener;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return mContacts.size() > 0 ? mContacts.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextName;
        private TextView mTextPhone;
        private ImageView mImageFavorite;
        private ImageView mImageCall;
        private OnClickContactItem mListener;

        public ViewHolder(View itemView, OnClickContactItem listener) {
            super(itemView);
            mTextName = itemView.findViewById(R.id.text_name);
            mTextPhone = itemView.findViewById(R.id.text_phone_number);
            mImageFavorite = itemView.findViewById(R.id.image_favorite);
            mImageFavorite.setOnClickListener(this);
            mImageCall = itemView.findViewById(R.id.image_call);
            mImageCall.setOnClickListener(this);
            mListener = listener;
        }

        public void setData(Contact contact) {
            mTextName.setText(contact.getName());
            mTextPhone.setText(contact.getPhoneNumber());
            mImageFavorite.setImageResource(contact.isFavorite() ?
                    R.drawable.ic_favorite_red_24dp : R.drawable.ic_favorite_border_black_24dp);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.image_call:
                    mListener.onClickCall(this.getLayoutPosition());
                    break;
                case R.id.image_favorite:
                    mListener.onClickFavorite(this.getLayoutPosition());
                    break;
            }
        }
    }
}
