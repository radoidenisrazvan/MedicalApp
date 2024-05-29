package com.example.medicalapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class SelectDiseaseFragment extends Fragment {

    private Spinner diseaseSpinner;
    private TextView recommendationTitle;
    private ListView diseaseListView;
    private ImageView pillImageView;
    private TextView pillDetailsText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_disease, container, false);

        diseaseSpinner = view.findViewById(R.id.diseaseSpinner);
        recommendationTitle = view.findViewById(R.id.recommendationTitle);
        diseaseListView = view.findViewById(R.id.diseaseListView);
        pillImageView = view.findViewById(R.id.pillImageView);
        pillDetailsText = view.findViewById(R.id.pillDetailsText);

        ArrayList<String> diseases = new ArrayList<>();
        diseases.add("Gripa");
        diseases.add("Răceală");
        diseases.add("Febră");
        diseases.add("Tuse");
        diseases.add("Durere de gât");
        diseases.add("Septicemie");
        diseases.add("Insuficiență hepatică");
        diseases.add("Diabet zaharat");
        diseases.add("Pneumonie");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, diseases);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diseaseSpinner.setAdapter(spinnerAdapter);
        diseaseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showRecommendations(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return view;
    }

    private void showRecommendations(int position) {
        recommendationTitle.setVisibility(View.VISIBLE);
        diseaseListView.setVisibility(View.VISIBLE);
        pillImageView.setVisibility(View.VISIBLE);
        pillDetailsText.setVisibility(View.VISIBLE);

        String recommendations;
        String pillDetails;
        int pillImageResource;
        switch (position) {
            case 0: // Gripa
                recommendations = getString(R.string.gripa_recomandari);
                pillDetails = getString(R.string.gripa_detalii_pilula);
                pillImageResource = R.drawable.gripa_pilula_image;
                break;
            case 1: // Raceala
                recommendations = getString(R.string.raceala_recomandari);
                pillDetails = getString(R.string.raceala_detalii_pilula);
                pillImageResource = R.drawable.raceala_pilula_image;
                break;
            case 2: // Febra
                recommendations = getString(R.string.febra_recomandari);
                pillDetails = getString(R.string.febra_detalii_pilula);
                pillImageResource = R.drawable.febra_pilula_image;
                break;
            case 3: // Tuse
                recommendations =  getString(R.string.tuse_recomandari);
                pillDetails = getString(R.string.tuse_detalii_pilula);
                pillImageResource = R.drawable.tuse_pilula_image;
                break;
            case 4: // Dureri de gat
                recommendations = getString(R.string.dureri_de_gat_recomandari);
                pillDetails = getString(R.string.dureri_de_gat_detalii_pilula);
                pillImageResource = R.drawable.dureri_de_gat_pilula_image;
                break;
            case 5: // Septicemie
                recommendations = getString(R.string.septicemie_recomandari);
                pillDetails = getString(R.string.septicemie_detalii_pilula);
                pillImageResource = R.drawable.septicemie_pilula_image;
                break;
            case 6: // Insuficienta Hepatica
                recommendations = getString(R.string.insuficineta_hepatica_recomandari);
                pillDetails = getString(R.string.insuficineta_detalii_pilula);
                pillImageResource = R.drawable.insuficienta_hepatica_pilula_image;
                break;
            case 7: // Diabet zaharat
                recommendations = getString(R.string.diabet_zaharat_recomandari);
                pillDetails = getString(R.string.diabet_zaharat_detalii_pilula);
                pillImageResource = R.drawable.diabet_zaharat_pilula_image;
                break;
            case 8: // Pneumonie
                recommendations = getString(R.string.pneumonie_recomandari);
                pillDetails = getString(R.string.pneumonie_detalii_pilula);
                pillImageResource = R.drawable.pneumonie_pilula_image;
                break;
            default:
                recommendations = "";
                pillDetails = "";
                pillImageResource = 0;
                break;
        }
        // afiseaza recomandările și detaliile pastilelor
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, new String[]{recommendations});
        diseaseListView.setAdapter(adapter);
        pillDetailsText.setText(pillDetails);
        pillImageView.setImageResource(pillImageResource);
    }

}
