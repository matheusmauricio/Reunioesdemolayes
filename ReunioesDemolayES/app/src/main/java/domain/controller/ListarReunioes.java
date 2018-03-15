package domain.controller;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matheus.reunioesdemolayes.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import domain.model.Calendario;
import domain.model.Capitulo;

public class ListarReunioes extends AppCompatActivity {

    private Integer numeroCap;
    private String descricao;
    private String grau;
    private String data;
    private String hora;
    private Integer numeroCapitulo;
    private String nomeCapitulo;
    private String aux;
    private ArrayList<String> param = new ArrayList<String>();
    private ListView listView;
    private TextView textViewNome;
    private android.os.Handler handler = new android.os.Handler();
    private IpServidor ipServidor = new IpServidor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_reunioes);

        Intent it = getIntent();
        numeroCap = it.getIntExtra("numeroCap", 0);

        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);
        textViewNome = (TextView) findViewById(R.id.textViewNomeCapitulo);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });


        listView = (ListView) findViewById(R.id.listViewReunioes);

        ListarReunioes.Task task = new ListarReunioes.Task();
        task.execute();


        listView.getSelectedItem();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //Capitulo capitulo = new Capitulo();
                //capitulo = (Capitulo) listView.getItemAtPosition(position);



            }
        });

    }

    public class Task extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(ListarReunioes.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            Runnable progressRunnable = new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
                }
            };
            handler.postDelayed(progressRunnable, 8000);

            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Listando Itens...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    ListarReunioes.Task.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {

            String url = ipServidor.getIpServidor()+"/listarReunioes.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("numeroCapitulo", String.valueOf(numeroCap)));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(valores));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                // ler o conteudo
                is = httpEntity.getContent();

            } catch (Exception e) {
                Log.e("log_tag", "Erro ao conectar com o banco de dados " + e.toString());
                Toast.makeText(ListarReunioes.this, "Tente novamente.", Toast.LENGTH_LONG).show();
            }

            try
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = "";

                while((line = br.readLine()) != null){
                    sb.append(line+"\n");
                }
                is.close();
                result = sb.toString();

            }catch(Exception e){
                Log.e("log_tag", "Erro ao converter o resultado " + e.toString());
            }
            return null;
        }

        protected void onPostExecute(Void v){

            try {
                JSONArray Jarray = new JSONArray(result);

                for (int i = 0; i < Jarray.length(); i++) {
                    JSONObject jsonObject = null;
                    jsonObject = Jarray.getJSONObject(i);

                    // output na tela
                    data = jsonObject.getString("data");
                    hora = jsonObject.getString("hora");
                    grau = jsonObject.getString("grau");
                    descricao = jsonObject.getString("descricao");
                    numeroCapitulo = jsonObject.getInt("numeroCapitulo");
                    nomeCapitulo = jsonObject.getString("nome");

                    Calendario calendario = new Calendario();

                    calendario.setNumeroCapitulo(numeroCapitulo);
                    calendario.setData(data);
                    calendario.setHora(hora);
                    calendario.setGrau(grau);
                    calendario.setDescricao(descricao);

                    aux = "• "+ data + " - " + hora + " - " + descricao + " - Grau " + grau;

                    param.add(aux);

                    textViewNome.setText("Calendário do " + nomeCapitulo + " nº " + numeroCapitulo);

                    ArrayAdapter<String> ad = new ArrayAdapter<String>(ListarReunioes.this, android.R.layout.simple_list_item_1, param);
                    listView.setAdapter(ad);

                }
                this.progressDialog.dismiss();

            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Falha ao carregar, por favor tente novamente mais tarde", Toast.LENGTH_LONG).show();
                Log.e("log_tag", "Error parsing data "+e.toString());
                this.progressDialog.dismiss();
                finish();
            }
        }
    }
}
