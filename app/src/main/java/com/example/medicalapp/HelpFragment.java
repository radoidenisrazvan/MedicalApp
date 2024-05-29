package com.example.medicalapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HelpFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);


        VideoView helpVideoView = view.findViewById(R.id.helpVideoView);
        String videoPath = "android.resource://" + requireContext().getPackageName() + "/" + R.raw.help_video;
        Uri uri = Uri.parse(videoPath);
        helpVideoView.setVideoURI(uri);


        MediaController mediaController = new MediaController(requireContext());
        helpVideoView.setMediaController(mediaController);
        mediaController.setAnchorView(helpVideoView);

        helpVideoView.start();

        return view;
    }
}
