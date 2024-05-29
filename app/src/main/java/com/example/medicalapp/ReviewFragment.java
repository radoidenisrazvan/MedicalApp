package com.example.medicalapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ReviewFragment extends Fragment {

    private DatabaseHelper databaseHelper;
    private RatingBar ratingBar;
    private EditText commentEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        databaseHelper = new DatabaseHelper(requireContext());

        ratingBar = view.findViewById(R.id.ratingBar);
        commentEditText = view.findViewById(R.id.reviewText);
        Button submitReviewButton = view.findViewById(R.id.submitReviewButton);

        submitReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                String comment = commentEditText.getText().toString().trim();

                if (comment.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter a comment.", Toast.LENGTH_SHORT).show();
                    return;
                }
                databaseHelper.addReview((int) rating, comment);
                Toast.makeText(requireContext(), "Review submitted successfully.", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}
