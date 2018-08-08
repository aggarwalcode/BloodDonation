package com.lifelineblood.lifelineblood.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lifelineblood.lifelineblood.R;
import com.lifelineblood.lifelineblood.activities.MainActivity;
import com.lifelineblood.lifelineblood.modelclass.BloodRequesteeDetails;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.lifelineblood.lifelineblood.activities.LoginActivity.auth;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RequestForm.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RequestForm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestForm extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView nameOfNeedy; TextView ageOfNeedy; TextView mobileNo; TextView placeofNeed;
    TextView inHospital; TextView need_response_text;
    Spinner spinnerBgP;
    Button btnRequestBlood;
    ProgressBar progressBar;
    RadioGroup radioGroup,bloodType;
    RadioButton radioSexButton,rbBloodType;
    RadioButton radioFemaleBut,radioPlatletBut;
    BloodRequesteeDetails bloodRequesteeDetails;



    String[] bloodGrpArr = new String[]{"Select Blood Group", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

    String name=null;       String hospital=null;
    String address=null;    String bloodgroup=null;
    String email=null;      String contactNum=null;
    String fBaseId=null;    String requestedBy=null;
    String sex=null;        int age = 0;
    String sdf;             boolean allowSubmit;
    String bloodTypeVal;    SimpleDateFormat timestamp=null;

    private OnFragmentInteractionListener mListener;
    DatabaseReference databaseReference;

    public RequestForm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RequestForm.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestForm newInstance(String param1, String param2) {
        RequestForm fragment = new RequestForm();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_request_form, container, false);

        spinnerBgP = (Spinner) view.findViewById(R.id.blood_group_rf);
        btnRequestBlood = (Button) view.findViewById(R.id.request_form);
        nameOfNeedy = (TextView) view.findViewById(R.id.nameRf);
        ageOfNeedy = (TextView) view.findViewById(R.id.ageRf);
        mobileNo = (TextView) view.findViewById(R.id.mobileNoRf);
        placeofNeed = (TextView) view.findViewById(R.id.locationRf);
        inHospital = (TextView) view.findViewById(R.id.hospitalRf);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGrpRf);
        bloodType = (RadioGroup) view.findViewById(R.id.blood_type);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_rf);
        radioFemaleBut = (RadioButton) view.findViewById(R.id.radioFemale);
        radioPlatletBut = (RadioButton) view.findViewById(R.id.radioPlatlet);
        need_response_text = RequestingForBlood.need_response_text;

        ArrayAdapter<String> adapterBgp = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_dropdown_item, bloodGrpArr
        );

        spinnerBgP.setAdapter(adapterBgp);
        btnRequestBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateRequest();
            }
        });

        return view;
    }

    private void generateRequest() {
        readFormDetails();
        validate();
        try {
            bloodRequesteeDetails = new BloodRequesteeDetails(
                    name, address, bloodgroup,email,
                    contactNum, sdf,fBaseId, requestedBy,
                    sex,age, hospital,bloodTypeVal
            );
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), ""+e,
                    Toast.LENGTH_LONG).show();
        }

        //Call Database Refrence to store user request
        databaseReference = FirebaseDatabase.getInstance().getReference();
        fBaseId = databaseReference.push().getKey();

        if(allowSubmit) {
            databaseReference.child("needRequests").child(fBaseId).setValue(bloodRequesteeDetails);
            databaseReference.child("users").child(auth.getUid()).child("isNeedy").setValue("true");
            Toast.makeText(getContext(),R.string.request_placed,Toast.LENGTH_SHORT).show();
            need_response_text.setText(R.string.request_placed);
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        }
        else {
            Toast.makeText(getContext(),"Check Errors",Toast.LENGTH_SHORT).show();
        }

    }

    private void readFormDetails() {

        name = nameOfNeedy.getText().toString().trim();
        hospital = inHospital.getText().toString().trim();
        address = placeofNeed.getText().toString().trim();

        if(!(ageOfNeedy.getText().toString().equals(""))){
            age = Integer.parseInt(ageOfNeedy.getText().toString());
        }


        contactNum= mobileNo.getText().toString().trim();
        bloodgroup = spinnerBgP.getSelectedItem().toString().trim();

        timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        sdf = timestamp.format(Calendar.getInstance().getTime());

        int id = radioGroup.getCheckedRadioButtonId();
        if(id>0) {
            radioSexButton = (RadioButton) getView().findViewById(id);
            sex = radioSexButton.getText().toString().trim().toLowerCase();
        }

        email = MainActivity.emailId;
        requestedBy = FirebaseAuth.getInstance().getUid();

        int idBtype = bloodType.getCheckedRadioButtonId();
        if(id>0) {
            rbBloodType = (RadioButton) getView().findViewById(idBtype);
            bloodTypeVal = rbBloodType.getText().toString().trim().toLowerCase();
        }

    }

    private void validate() {

        allowSubmit =  true;

        if(name.length()<3){
            nameOfNeedy.setError("Fill the name");
            allowSubmit = false;
        }

        if(sex == null){
            radioFemaleBut.setError("Select one");
            allowSubmit = false;
        }

        if(contactNum.length()<10){
            mobileNo.setError("Fill currect mobile number");
            allowSubmit = false;
        }

        if (spinnerBgP.getSelectedItem().toString().equals("Select Blood Group") ||bloodgroup==null){
            ((TextView)spinnerBgP.getSelectedView()).setError("Select one");
            allowSubmit = false;
        }

        if(age == 0){
            ageOfNeedy.setError("Fill age");
            allowSubmit = false;
        }

        if(address.length()<3){
            placeofNeed.setError("Fill correct address");
            allowSubmit = false;
        }

        if(hospital.length()<3){
            inHospital.setError("Fill hospital name");
            allowSubmit = false;
        }

        if(bloodTypeVal == null){
            radioPlatletBut.setError("Select one");
            allowSubmit = false;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public boolean allowBackPressed() {
        return true;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
