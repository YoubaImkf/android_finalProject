package com.example.demofragments.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demofragments.R;

import java.util.ArrayList;


// SOURCES :
// First course project - AndroidStudioProjects\MyApp
// https://developer.android.com/develop/ui/views/layout/recyclerview
// delete : https://www.youtube.com/watch?v=LQmGU3UCOPQ&ab_channel=CodeVedanam
public class ResponseAdapter extends RecyclerView.Adapter<ResponseAdapter.ResponseHolder> {

    private final ArrayList<String> responsesList;
    private OnDeleteClickListener onDeleteClickListener;

    public ResponseAdapter(ArrayList<String> responsesList) {
        this.responsesList = responsesList;
    }

    public static class ResponseHolder extends RecyclerView.ViewHolder {
        private final TextView responseTextView;
        private final View deleteButton;

        public ResponseHolder(@NonNull View itemView) {
            super(itemView);
            responseTextView = itemView.findViewById(R.id.response_text_view);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }

        public void bind(String response, OnDeleteClickListener onDeleteClickListener) {
            responseTextView.setText(response);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClickListener.onDeleteClick(getAdapterPosition());
                }
            });

        }


    }

    @NonNull
    @Override
    public ResponseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reponses_item, parent, false);
        return new ResponseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ResponseHolder holder, int position) {
        String response = responsesList.get(position);
        holder.bind(response, onDeleteClickListener);
    }

    @Override
    public int getItemCount() {
        return responsesList.size();
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        onDeleteClickListener = listener;
    }


    // Interface
    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }
}