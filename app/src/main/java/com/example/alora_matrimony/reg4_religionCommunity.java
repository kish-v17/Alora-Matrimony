package com.example.alora_matrimony;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class reg4_religionCommunity extends Fragment {
    AppCompatButton btnContinue;
    Spinner spCommunity,spReligion,spSubCom,spState,spCity;
    String religion,community,subCommunity,state,city;
    ArrayAdapter<CharSequence> subComAdd,cityAdd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_reg4_religion_community, container, false);

        btnContinue= view.findViewById(R.id.btnContinue);
        spReligion=view.findViewById(R.id.spReligion);
        spCommunity=view.findViewById(R.id.spCommunity);
        spSubCom=view.findViewById(R.id.spSubCommunity);
        spCity=view.findViewById(R.id.spCity);
        spState=view.findViewById(R.id.spState);

        subComAdd=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.default_subcommunity));
        spSubCom.setAdapter(subComAdd);

        cityAdd=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.defaultCity));
        spCity.setAdapter(cityAdd);
        spReligion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                religion=spReligion.getSelectedItem().toString();
                int pid=parent.getId();
                if(pid==R.id.spReligion){
                    switch (religion) {
                        case "Religion":
                            subComAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.default_subcommunity,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Hindu":
                            subComAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.hindu_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Muslim":
                            subComAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.muslim_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Christian":
                            subComAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.christian_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Jain":
                            subComAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.jain_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Sikh":
                            subComAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.sikh_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Buddhist":
                            subComAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.buddhist_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Parsi":
                            subComAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.parsi_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Jewish":
                            subComAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.jewish_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Other":
                            subComAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.other_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "No Religion":
                            subComAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.no_religion_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Spiritual - not religious":
                            subComAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.spiritual_not_religious_communities,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        default:
                            subComAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.default_subcommunity, android.R.layout.simple_spinner_dropdown_item);
                            break;
                    }
                    subComAdd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spSubCom.setAdapter(subComAdd);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state=spState.getSelectedItem().toString();

                int pid=parent.getId();
                if(pid==R.id.spState){
                    switch (state) {
                        case "Select State":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.defaultCity,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Andaman and Nicobar Islands":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.andaman_and_nicobar_islands,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Andhra Pradesh":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.andhra_pradesh,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Arunachal Pradesh":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.arunachal_pradesh,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Assam":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.assam,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Bihar":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.bihar,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Chhattisgarh":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.chhattisgarh,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Chandigarh":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.chandigarh,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Dadra and Nagar Haveli":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.dadra_and_nagar_haveli,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Daman and Diu":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.daman_and_diu,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Delhi":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.delhi,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Goa":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.goa,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Gujarat":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.gujarat,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Haryana":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.haryana,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Himachal Pradesh":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.himachal_pradesh,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Jharkhand":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.jharkhand,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Karnataka":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.karnataka,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Kerala":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.kerala,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Lakshadweep":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.lakshadweep,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Madhya Pradesh":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.madhya_pradesh,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Maharashtra":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.maharashtra,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Manipur":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.manipur,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Meghalaya":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.meghalaya,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Mizoram":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.mizoram,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Nagaland":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.nagaland,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Odisha":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.odisha,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Pondicherry":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.pondicherry,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Punjab":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.punjab,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Rajasthan":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.rajasthan,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Sikkim":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.sikkim,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Tamil Nadu":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.tamil_nadu,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Telangana":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.telangana,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Tripura":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.tripura,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Uttar Pradesh":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.uttar_pradesh,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "Uttarakhand":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.uttarakhand,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case "West Bengal":
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.west_bengal,android.R.layout.simple_spinner_dropdown_item);
                            break;
                        default:
                            cityAdd = ArrayAdapter.createFromResource(parent.getContext(),R.array.defaultCity,android.R.layout.simple_spinner_dropdown_item);
                            break;
                    }
                    cityAdd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spCity.setAdapter(cityAdd);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                community=spCommunity.getSelectedItem().toString();
                subCommunity=spSubCom.getSelectedItem().toString();
                city=spCity.getSelectedItem().toString();

                if (religion.equals("Select Religion")) {
                    Toast.makeText(getActivity(), "Please select a religion", Toast.LENGTH_SHORT).show();
                } else if (community.equals("Select Community")) {
                    Toast.makeText(getActivity(), "Please select a community", Toast.LENGTH_SHORT).show();
                } else if (subCommunity.equals("Select Sub Community")) {
                    Toast.makeText(getActivity(), "Please select a sub-community", Toast.LENGTH_SHORT).show();
                } else if (state.equals("Select State")) {
                    Toast.makeText(getActivity(), "Please select a state", Toast.LENGTH_SHORT).show();
                } else if (city.equals("Select City")) {
                    Toast.makeText(getActivity(), "Please select a city", Toast.LENGTH_SHORT).show();
                }else {
                    reg5_maritalHeightWeightDiet marital = new reg5_maritalHeightWeightDiet();
                    Bundle b = getArguments();
                    b.putString("religion", religion);
                    b.putString("community", community);
                    b.putString("subCommunity", subCommunity);
                    b.putString("state", state);
                    b.putString("city", city);
                    marital.setArguments(b);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, marital).addToBackStack(null).commit();
                }
            }
        });
        return view;
    }

}