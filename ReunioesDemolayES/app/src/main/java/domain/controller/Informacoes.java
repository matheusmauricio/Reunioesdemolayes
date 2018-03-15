package domain.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matheus.reunioesdemolayes.R;

import java.util.ArrayList;

public class Informacoes extends AppCompatActivity {

    private Spinner spinnerOpcao;
    private ArrayList<String> arrayOpcao = new ArrayList<>();
    private TextView textView1;
    private String opcaoEscolhida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes);

        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);
        spinnerOpcao = (Spinner) findViewById(R.id.spinnerOpcao);
        textView1 = (TextView) findViewById(R.id.textView);

        arrayOpcao.add("Grande Capítulo Estadual - GCE");
        arrayOpcao.add("Gabinete Estadual de Liderança Juvenil");
        arrayOpcao.add("Alumni ES");

        ArrayAdapter<String> ad = new ArrayAdapter<String>(Informacoes.this, android.R.layout.simple_spinner_dropdown_item, arrayOpcao);
        spinnerOpcao.setAdapter(ad);

        spinnerOpcao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opcaoEscolhida = spinnerOpcao.getItemAtPosition(position).toString();

                if (opcaoEscolhida == "Grande Capítulo Estadual - GCE"){
                    textView1.setText("• GME: Guilherme Antônio Monteiro Gomes - CID: 82609\n\n" +
                            "• GMEA: Levy Machado de Souza - CID: 108375\n\n • Secretário Executivo Estadual: Samuel Pereira Leite - CID: 93344" +
                            "\n\n • Grande Secretário Estadual de Honrarias e Prêmios: " +
                            "Gustavo Zocatelli Tardin de Moraes - CID: 81340\n\n • Grande Secretário Estadual de Legislação: " +
                            "Kaio Dassie Schubert - CID: 91536\n\n • Grande Secretário Estadual de Expansão: Leonardo Teixeira" +
                            "Guimarães - CID: 77605\n\n • Grande Secretário Estadual de Ritualística: Matheus Mauricio de Souza Araujo - CID: 79346" +
                            "\n\n • Grande Secretário Estadual de Comunicação: Ronaldo Viana da Cunha Santos Filho - CID: 105607" +
                            "\n\n • Grande Secretário Estadual de Planejamento: Rafael Oscar Souza Miranda - CID: 76261" +
                            "\n\n • Grande Secretário Estadual de Filantropia: José Junior Alexandre Barbosa - CID: 110209");
                } else if(opcaoEscolhida == "Gabinete Estadual de Liderança Juvenil"){
                    textView1.setText("• MCE: Silvestre Magnago de Mattos Panciere - CID: 93345\n\n" +
                            "• MCEA: Carlos Henrique de Souza Gomes - CID: 93338\n\n • Secretário Geral: William Magnago de Mattos Panciere - CID: 108308" +
                            "\n\n • Tesoureiro: Erick Picoli Crisanto - CID: 106925\n\n • Secretário de Comunicação: Filipe Benevides Mendonça - CID:108854\n\n" +
                            "• Secretário de Ritualística: João Henrique Teixeira Cateem - CID: 105387\n\n • Secretário de Filantropia: Wilson da Silva Goltara " +
                            "- CID: 108613");
                } else if (opcaoEscolhida == "Alumni ES"){
                    textView1.setText("• Presidente: Ronaldo Viana da Cunha Santos Filho - CID: 105607\n\n" +
                            "• Vice Presidente: Matheus Mauricio de Souza Araujo - CID: 79346\n\n • Secretário: " +
                            "Gustavo Zocatelli Tardin de Moraes - CID: 81340\n\n • Tesoureiro: João Victor dos Reis Damaceno - CID: 89136" +
                            "\n\n • Secretário de Ação Social: Jordan Tomazelli Lemos - CID: 94280");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });

    }
}
