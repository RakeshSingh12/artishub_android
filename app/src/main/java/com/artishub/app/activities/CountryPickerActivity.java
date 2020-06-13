package com.artishub.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.artishub.app.adapter.CountryPickerAdapter;
import com.artishub.app.model.Country;
import com.artishub.app.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CountryPickerActivity extends AppCompatActivity {
    ListView listView;
    CountryPickerAdapter adapter;
    EditText searchEditText;
    List<Country> countryList = new ArrayList<>();


    //**************************All CountryName and countryCode**************************//
    public static final Country[] COUNTRIES = {
            new Country("AF", "Afghanistan", "+93"),
            new Country("AG", "Antigua and Barbuda", "+1"),
            new Country("AI", "Anguilla", "+1"),
            new Country("AL", "Albania", "+355"),
            new Country("AM", "Armenia", "+374"),
            new Country("AO", "Angola", "+244"),
            new Country("AQ", "Antarctica", "+672"),
            new Country("AR", "Argentina", "+54"),
            new Country("AS", "AmericanSamoa", "+1"),
            new Country("AT", "Austria", "+43"),
            new Country("AU", "Australia", "+61"),
            new Country("AW", "Aruba", "+297"),
            new Country("AX", "Åland Islands", "+358"),
            new Country("AZ", "Azerbaijan", "+994"),
            new Country("BA", "Bosnia and Herzegovina", "+387"),
            new Country("BB", "Barbados", "+1"),
            new Country("BD", "Bangladesh", "+880"),
            new Country("BE", "Belgium", "+32"),
            new Country("BF", "Burkina Faso", "+226"),
            new Country("BG", "Bulgaria", "+359"),
            new Country("BH", "Bahrain", "+973"),
            new Country("BI", "Burundi", "+257"),
            new Country("BJ", "Benin", "+229"),
            new Country("BL", "Saint Barthélemy", "+590"),
            new Country("BM", "Bermuda", "+1"),
            new Country("BN", "Brunei Darussalam", "+673"),
            new Country("BO", "Bolivia, Plurinational State of", "+591"),
            new Country("BQ", "Bonaire", "+599"),
            new Country("BR", "Brazil", "+55"),
            new Country("BS", "Bahamas", "+1"),
            new Country("BT", "Bhutan", "+975"),
            new Country("BV", "Bouvet Island", "+47"),
            new Country("BW", "Botswana", "+267"),
            new Country("BY", "Belarus", "+375"),
            new Country("BZ", "Belize", "+501"),
            new Country("CA", "Canada", "+1"),
            new Country("CC", "Cocos (Keeling) Islands", "+61"),
            new Country("CD", "Congo, The Democratic Republic of the", "+243"),
            new Country("CF", "Central African Republic", "+236"),
            new Country("CG", "Congo", "+242"),
            new Country("CI", "Ivory Coast", "+225"),
            new Country("CK", "Cook Islands", "+682"),
            new Country("KM", "Comoros", "+269"),
            new Country("CL", "Chile", "+56"),
            new Country("CM", "Cameroon", "+237"),
            new Country("CN", "China", "+86"),
            new Country("CO", "Colombia", "+57"),
            new Country("CR", "Costa Rica", "+506"),
            new Country("CU", "Cuba", "+53"),
            new Country("CV", "Cape Verde", "+238"),
            new Country("CW", "Curacao", "+599"),
            new Country("CX", "Christmas Island", "+61"),
            new Country("CY", "Cyprus", "+357"),
            new Country("CZ", "Czech Republic", "+420"),
            new Country("DE", "Germany", "+49"),
            new Country("DJ", "Djibouti", "+253"),
            new Country("DK", "Denmark", "+45"),
            new Country("DM", "Dominica", "+1"),
            new Country("DO", "Dominican Republic", "+1"),
            new Country("DZ", "Algeria", "+213"),
            new Country("EC", "Ecuador", "+593"),
            new Country("EE", "Estonia", "+372"),
            new Country("EG", "Egypt", "+20"),
            new Country("EH", "Western Sahara", "+212"),
            new Country("ER", "Eritrea", "+291"),
            new Country("ES", "Spain", "+34"),
            new Country("ET", "Ethiopia", "+251"),
            new Country("FI", "Finland", "+358"),
            new Country("FJ", "Fiji", "+679"),
            new Country("FK", "Falkland Islands (Malvinas)", "+500"),
            new Country("FM", "Micronesia, Federated States of", "+691"),
            new Country("FO", "Faroe Islands", "+298"),
            new Country("FR", "France", "+33"),
            new Country("GA", "Gabon", "+241"),
            new Country("GB", "United Kingdom", "+44"),
            new Country("GD", "Grenada", "+1"),
            new Country("GE", "Georgia", "+995"),
            new Country("GF", "French Guiana", "+594"),
            new Country("GG", "Guernsey", "+44"),
            new Country("GH", "Ghana", "+233"),
            new Country("GI", "Gibraltar", "+350"),
            new Country("GL", "Greenland", "+299"),
            new Country("GM", "Gambia", "+220"),
            new Country("GN", "Guinea", "+224"),
            new Country("GP", "Guadeloupe", "+590"),
            new Country("GQ", "Equatorial Guinea", "+240"),
            new Country("GR", "Greece", "+30"),
            new Country("GS", "South Georgia and the South Sandwich Islands", "+500"),
            new Country("GT", "Guatemala", "+502"),
            new Country("GU", "Guam", "+1"),
            new Country("GW", "Guinea-Bissau", "+245"),
            new Country("GY", "Guyana", "+595"),
            new Country("HK", "Hong Kong", "+852"),
            new Country("HN", "Honduras", "+504"),
            new Country("HR", "Croatia", "+385"),
            new Country("HT", "Haiti", "+509"),
            new Country("HU", "Hungary", "+36"),
            new Country("ID", "Indonesia", "+62"),
            new Country("IE", "Ireland", "+353"),
            new Country("IL", "Israel", "+972"),
            new Country("IM", "Isle of Man", "+44"),
            new Country("IN", "India", "+91"),
            new Country("IO", "British Indian Ocean Territory", "+246"),
            new Country("IQ", "Iraq", "+964"),
            new Country("IR", "Iran", "+98"),
            new Country("IS", "Iceland", "+354"),
            new Country("IT", "Italy", "+39"),
            new Country("JE", "Jersey", "+44"),
            new Country("JM", "Jamaica", "+1"),
            new Country("JO", "Jordan", "+962"),
            new Country("JP", "Japan", "+81"),
            new Country("KE", "Kenya", "+254"),
            new Country("KG", "Kyrgyzstan", "+996"),
            new Country("KH", "Cambodia", "+855"),
            new Country("KI", "Kiribati", "+686"),
            new Country("LR", "Liberia", "+231"),
            new Country("LS", "Lesotho", "+266"),
            new Country("LT", "Lithuania", "+370"),
            new Country("LU", "Luxembourg", "+352"),
            new Country("LV", "Latvia", "+371"),
            new Country("LY", "Libyan Arab Jamahiriya", "+218"),
            new Country("KN", "Saint Kitts and Nevis", "+1"),
            new Country("KP", "North Korea", "+850"),
            new Country("KW", "Kuwait", "+965"),
            new Country("KY", "Cayman Islands", "+345"),
            new Country("KZ", "Kazakhstan", "+7"),
            new Country("LA", "Lao People's Democratic Republic", "+856"),
            new Country("LB", "Lebanon", "+961"),
            new Country("LI", "Liechtenstein", "+423"),
            new Country("LK", "Sri Lanka", "+94"),
            new Country("WS", "Samoa", "+685"),
            new Country("LC", "Saint Lucia", "+1"),
            new Country("ZA", "South Africa", "+27"),
            new Country("CH", "Switzerland", "+41"),
            new Country("KR", "South Korea", "+82"),
            new Country("MA", "Morocco", "+212"),
            new Country("MC", "Monaco", "+377"),
            new Country("MD", "Moldova, Republic of", "+373"),
            new Country("ME", "Montenegro", "+382"),
            new Country("MF", "Saint Martin", "+590"),
            new Country("MG", "Madagascar", "+261"),
            new Country("MH", "Marshall Islands", "+692"),
            new Country("MK", "Macedonia, The Former Yugoslav Republic of", "+389"),
            new Country("ML", "Mali", "+223"),
            new Country("MM", "Myanmar", "+95"),
            new Country("MN", "Mongolia", "+976"),
            new Country("MO", "Macao", "+853"),
            new Country("MP", "Northern Mariana Islands", "+1"),
            new Country("MQ", "Martinique", "+596"),
            new Country("YT", "Mayotte", "+262"),
            new Country("MR", "Mauritania", "+222"),
            new Country("MS", "Montserrat", "+1"),
            new Country("MT", "Malta", "+356"),
            new Country("MU", "Mauritius", "+230"),
            new Country("MV", "Maldives", "+960"),
            new Country("MW", "Malawi", "+265"),
            new Country("MX", "Mexico", "+52"),
            new Country("MY", "Malaysia", "+60"),
            new Country("MZ", "Mozambique", "+258"),
            new Country("NA", "Namibia", "+264"),
            new Country("NC", "New Caledonia", "+687"),
            new Country("NE", "Niger", "+227"),
            new Country("NF", "Norfolk Island", "+672"),
            new Country("NG", "Nigeria", "+234"),
            new Country("NI", "Nicaragua", "+505"),
            new Country("NL", "Netherlands", "+31"),
            new Country("NO", "Norway", "+47"),
            new Country("NP", "Nepal", "+977"),
            new Country("NR", "Nauru", "+674"),
            new Country("NU", "Niue", "+683"),
            new Country("NZ", "New Zealand", "+64"),
            new Country("OM", "Oman", "+968"),
            new Country("PA", "Panama", "+507"),
            new Country("PE", "Peru", "+51"),
            new Country("PF", "French Polynesia", "+689"),
            new Country("PG", "Papua New Guinea", "+675"),
            new Country("PH", "Philippines", "+63"),
            new Country("PK", "Pakistan", "+92"),
            new Country("PL", "Poland", "+48"),
            new Country("PM", "Saint Pierre and Miquelon", "+508"),
            new Country("PN", "Pitcairn", "+872"),
            new Country("PR", "Puerto Rico", "+1"),
            new Country("PS", "Palestinian Territory, Occupied", "+970"),
            new Country("PT", "Portugal", "+351"),
            new Country("PW", "Palau", "+680"),
            new Country("PY", "Paraguay", "+595"),
            new Country("QA", "Qatar", "+974"),
            new Country("RE", "Réunion", "+262"),
            new Country("RO", "Romania", "+40"),
            new Country("RS", "Serbia", "+381"),
            new Country("RU", "Russia", "+7"),
            new Country("RW", "Rwanda", "+250"),
            new Country("SA", "Saudi Arabia", "+966"),
            new Country("SB", "Solomon Islands", "+677"),
            new Country("SC", "Seychelles", "+248"),
            new Country("SD", "Sudan", "+249"),
            new Country("SE", "Sweden", "+46"),
            new Country("SG", "Singapore", "+65"),
            new Country("SH", "Saint Helena, Ascension and Tristan Da Cunha", "+290"),
            new Country("SI", "Slovenia", "+386"),
            new Country("SJ", "Svalbard and Jan Mayen", "+47"),
            new Country("SK", "Slovakia", "+421"),
            new Country("SL", "Sierra Leone", "+232"),
            new Country("SM", "San Marino", "+378"),
            new Country("SN", "Senegal", "+221"),
            new Country("SO", "Somalia", "+252"),
            new Country("SR", "Suriname", "+597"),
            new Country("SS", "South Sudan", "+211"),
            new Country("ST", "Sao Tome and Principe", "+239"),
            new Country("SV", "El Salvador", "+503"),
            new Country("SX", "Sint Maarten", "+1"),
            new Country("SY", "Syrian Arab Republic", "+963"),
            new Country("SZ", "Swaziland", "+268"),
            new Country("TC", "Turks and Caicos Islands", "+1"),
            new Country("TD", "Chad", "+235"),
            new Country("TF", "French Southern Territories", "+262"),
            new Country("TG", "Togo", "+228"),
            new Country("TH", "Thailand", "+66"),
            new Country("TJ", "Tajikistan", "+992"),
            new Country("TK", "Tokelau", "+690"),
            new Country("TL", "East Timor", "+670"),
            new Country("TM", "Turkmenistan", "+993"),
            new Country("TN", "Tunisia", "+216"),
            new Country("TO", "Tonga", "+676"),
            new Country("TR", "Turkey", "+90"),
            new Country("TT", "Trinidad and Tobago", "+1"),
            new Country("TV", "Tuvalu", "+688"),
            new Country("TW", "Taiwan", "+886"),
            new Country("TZ", "Tanzania, United Republic of", "+255"),
            new Country("UA", "Ukraine", "+380"),
            new Country("UG", "Uganda", "+256"),
            new Country("US", "United States", "+1"),
            new Country("AE", "United Arab Emirates", "+971"),
            new Country("AD", "Andorra", "+376"),
            new Country("UY", "Uruguay", "+598"),
            new Country("UZ", "Uzbekistan", "+998"),
            new Country("VA", "Holy See (Vatican City State)", "+379"),
            new Country("VC", "Saint Vincent and the Grenadines", "+1"),
            new Country("VE", "Venezuela, Bolivarian Republic of", "+58"),
            new Country("VG", "Virgin Islands, British", "+1"),
            new Country("VI", "Virgin Islands, U.S.", "+1"),
            new Country("VN", "Viet Nam", "+84"),
            new Country("VU", "Vanuatu", "+678"),
            new Country("WF", "Wallis and Futuna", "+681"),
            new Country("YE", "Yemen", "+967"),
            new Country("ZM", "Zambia", "+260"),
            new Country("ZW", "Zimbabwe", "+263"),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_picker);


        //*******************Add CountryName and countryCode in countryList ******************//
       /* for (int i = 0; i < COUNTRIES.length; i++) {
            countryList.add(COUNTRIES[i]);
        }*/
       countryList.addAll(getAllCountries());

        //*CountryList sorted method*//


        //********************************adapter setUp******************************************//
        final CountryPickerAdapter adapter = new CountryPickerAdapter(CountryPickerActivity.this, countryList);
        listView = (ListView) findViewById(R.id.country_code_picker_listview);
        listView.setAdapter(adapter);


        //*****************************Search EditText Method************************************//
        searchEditText = (EditText) findViewById(R.id.country_code_picker_search);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



            }

            @Override
            public void afterTextChanged(Editable s) {
                countryList.clear();
                String text = s.toString();
                for (int i = 0; i < COUNTRIES.length; i++) {
                    if (COUNTRIES[i].getCountryName().toLowerCase().startsWith(text.toLowerCase())) {

                        countryList.add(COUNTRIES[i]);
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        adapter.notifyDataSetChanged();
                    }


                }
            }
        });


        //****************************clickListener on listView Item******************************//
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent("CountryCodeSelection");
                intent.putExtra("position", countryList.get(position).getDileCode());
                LocalBroadcastManager.getInstance(CountryPickerActivity.this).sendBroadcast(intent);
                finish();
            }
        });

    }
    private static List<Country> allCountriesList;

    public static List<Country> getAllCountries() {
        if (allCountriesList == null) {
            allCountriesList = Arrays.asList(COUNTRIES);
        }
        Collections.sort(allCountriesList, new Comparator<Country>() {
            @Override
            public int compare(final Country object1, final Country object2) {
                return object1.getCountryName().compareTo(object2.getCountryName());
            }
        });
        return allCountriesList;
    }


}
