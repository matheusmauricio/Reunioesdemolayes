package domain.controller;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matheus.reunioesdemolayes.R;

public class CapituloDetalhado extends AppCompatActivity {

    private Button buttonListarCalendario1;
    private Button buttonComoChegar1;
    private ImageView imageView;
    private TextView textViewInfo;
    private Integer numeroCap;
    private String nomeCap;
    private static String enderecoCap;
    private String lojaPatrocinadora;
    private String cidadeCapitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capitulo_detalhado);

        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);
        buttonListarCalendario1 = (Button) findViewById(R.id.buttonListarCalendario);
        buttonComoChegar1 = (Button) findViewById(R.id.buttonComoChegar);
        textViewInfo = (TextView) findViewById(R.id.textViewInformacoes);
        imageView = (ImageView) findViewById(R.id.imageViewActivity);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });

        Intent it = getIntent();
        numeroCap = it.getIntExtra("numeroCap", 0);
        nomeCap = it.getStringExtra("nomeCap");
        enderecoCap = it.getStringExtra("enderecoCap");
        lojaPatrocinadora = it.getStringExtra("lojaPatrocinadora");
        cidadeCapitulo = it.getStringExtra("cidadeCap");

        if (numeroCap == 391){ //Cachoeiro
            textViewInfo.setText(nomeCap + ", nº " + numeroCap + "\n" +
                    "MC Gestão 2018/1: Luan Fardim\n" +
                    "PCC: Sinval de Oliveira Bastos\n" +
                    "Loja Patrocinadora: " + lojaPatrocinadora + "\n" +
                    "Endereço: " + enderecoCap);
            imageView.setImageResource(R.drawable.brasao_cachoeiro);
        } else if (numeroCap == 123){ //Vitória
            textViewInfo.setText(nomeCap + ", nº " + numeroCap + "\n" +
                    "MC Gestão 2018/1: Arthur Motta\n" +
                    "PCC: Paulo Finamore \n" +
                    "Loja Patrocinadora: " + lojaPatrocinadora + "\n" +
                    "Endereço: " + enderecoCap);
            imageView.setImageResource(R.drawable.brasao_vitoria);
        } else if (numeroCap == 463){ //Alegre
            textViewInfo.setText(nomeCap + ", nº " + numeroCap + "\n" +
                    "MC Gestão 2018/1: João Henrique Cateem\n" +
                    "PCC: Thallis Abdala Prata\n" +
                    "Loja Patrocinadora: " + lojaPatrocinadora + "\n" +
                    "Endereço: " + enderecoCap);
            imageView.setImageResource(R.drawable.brasao_demolay);
        } else if (numeroCap == 633){ //Pinheiros
            textViewInfo.setText(nomeCap + ", nº " + numeroCap + "\n" +
                    "MC Gestão 2018/1: Thales Mascarenhas\n" +
                    "PCC: Leonardo Bayer Favaro\n" +
                    "Loja Patrocinadora: " + lojaPatrocinadora + "\n" +
                    "Endereço: " + enderecoCap);
            imageView.setImageResource(R.drawable.brasao_agl);
        } else if (numeroCap == 675){ //Linhares
            textViewInfo.setText(nomeCap + ", nº " + numeroCap + "\n" +
                    "MC Gestão 2018/1: Mozart Marques Pereira Filho \n" +
                    "PCC: Uellington Damazio\n" +
                    "Loja Patrocinadora: " + lojaPatrocinadora + "\n" +
                    "Endereço: " + enderecoCap);
            imageView.setImageResource(R.drawable.brasao_linhares);
        } else if (numeroCap == 35){ //Convento Nobres Cavaleiros do Templo de Salomão
            textViewInfo.setText(nomeCap + ", nº " + numeroCap + "\n" +
                    "ICC: Kaio Schubert\n" +
                    "PCC: Sinval de Oliveira Bastos\n" +
                    "Loja Patrocinadora: " + lojaPatrocinadora + "\n" +
                    "Endereço: " + enderecoCap);
            imageView.setImageResource(R.drawable.brasao_cavalaria);
        } else if (numeroCap == 113){ //Convento Crux Sacra
            textViewInfo.setText(nomeCap + ", nº " + numeroCap + "\n" +
                    "ICC: Samuel Pereira Leite\n" +
                    "PCC: Uellington Damazio \n" +
                    "Loja Patrocinadora: " + lojaPatrocinadora + "\n" +
                    "Endereço: " + enderecoCap);
            imageView.setImageResource(R.drawable.brasao_cavalaria);
        }


    }

    public void listarCalendario(View view){
        Intent irParaTelaListarReunioes = new Intent(getBaseContext(), ListarReunioes.class);
        irParaTelaListarReunioes.putExtra("numeroCap", numeroCap);

        startActivity(irParaTelaListarReunioes);
    }


    public void comoChegar (View view){
        try {
            Intent abrirMapa = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr=&daddr=" + enderecoCap));

            /*
            * Se você quiser que o usuário vá direto para o aplicativo do Google Maps, use a linha abaixo.
            * Caso não queira (de opções para o usuário abrir em outros aplicativos de mapas no celular), apenas apague a linha abaixo.
            */
            //abrirMapa.setComponent(new ComponentName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity"));

            startActivity(abrirMapa);
        } catch (Exception ex) {
            Toast.makeText(this, "Verifique se o Google Maps está instalado em seu dispositivo", Toast.LENGTH_SHORT).show();
        }
    }

}
