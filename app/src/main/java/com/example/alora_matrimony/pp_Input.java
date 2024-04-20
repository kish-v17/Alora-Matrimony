package com.example.alora_matrimony;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.alora_matrimony.databinding.FragmentPpInputBinding;


public class pp_Input extends Fragment {
    FragmentPpInputBinding b;
    String gender,religion,community,subCom,state,city,maritalStatus,height,weight,diet,qual,income,occupation;
    ArrayAdapter<CharSequence> subComAd,cityAd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b=FragmentPpInputBinding.inflate(inflater,container,false);
        View view=b.getRoot();

        subComAd=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.default_subcommunity));
        b.ppSpSubCommunity.setAdapter(subComAd);

        cityAd=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.defaultCity));
        b.ppSpCity.setAdapter(cityAd);

        b.ppSpReligion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                religion=b.ppSpReligion.getSelectedItem().toString();
                int pid=parent.getId();
                if(pid==R.id.ppSpReligion){
                    switch (religion) {
                        case "Religion":
                            subComAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.default_subcommunity,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Hindu":
                            subComAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.hindu_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Muslim":
                            subComAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.muslim_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Christian":
                            subComAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.christian_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Jain":
                            subComAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.jain_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Sikh":
                            subComAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.sikh_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Buddhist":
                            subComAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.buddhist_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Parsi":
                            subComAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.parsi_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Jewish":
                            subComAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.jewish_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Other":
                            subComAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.other_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "No Religion":
                            subComAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.no_religion_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Spiritual - not religious":
                            subComAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.spiritual_not_religious_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        default:
                            subComAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.default_subcommunity, android.R.layout.simple_spinner_dropdown_item);
                            break;
                    }
                    subComAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    b.ppSpSubCommunity.setAdapter(subComAd);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        b.ppSpState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state=b.ppSpState.getSelectedItem().toString();

                int pid=parent.getId();
                if(pid==R.id.ppSpState){
                    switch (state) {
                        case "Select State":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.defaultCity,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Andaman and Nicobar Islands":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.andaman_and_nicobar_islands,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Andhra Pradesh":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.andhra_pradesh,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Arunachal Pradesh":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.arunachal_pradesh,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Assam":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.assam,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Bihar":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.bihar,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Chhattisgarh":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.chhattisgarh,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Chandigarh":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.chandigarh,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Dadra and Nagar Haveli":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.dadra_and_nagar_haveli,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Daman and Diu":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.daman_and_diu,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Delhi":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.delhi,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Goa":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.goa,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Gujarat":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.gujarat,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Haryana":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.haryana,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Himachal Pradesh":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.himachal_pradesh,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Jharkhand":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.jharkhand,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Karnataka":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.karnataka,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Kerala":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.kerala,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Lakshadweep":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.lakshadweep,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Madhya Pradesh":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.madhya_pradesh,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Maharashtra":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.maharashtra,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Manipur":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.manipur,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Meghalaya":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.meghalaya,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Mizoram":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.mizoram,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Nagaland":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.nagaland,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Odisha":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.odisha,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Pondicherry":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.pondicherry,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Punjab":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.punjab,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Rajasthan":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.rajasthan,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Sikkim":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.sikkim,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Tamil Nadu":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.tamil_nadu,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Telangana":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.telangana,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Tripura":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.tripura,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Uttar Pradesh":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.uttar_pradesh,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Uttarakhand":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.uttarakhand,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "West Bengal":
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.west_bengal,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        default:
                            cityAd = ArrayAdapter.createFromResource(parent.getContext(),R.array.defaultCity,android.R.layout.simple_spinner_dropdown_item);
                            break;
                    }
                    cityAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    b.ppSpCity.setAdapter(cityAd);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
}