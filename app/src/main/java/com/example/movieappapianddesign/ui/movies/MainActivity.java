package com.example.movieappapianddesign.ui.movies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.movieappapianddesign.R;
import com.example.movieappapianddesign.adapter.ItemOnClickListenerPopular;
import com.example.movieappapianddesign.adapter.ItemOnClickListenerUpcoming;
import com.example.movieappapianddesign.adapter.PopularMovieAdapter;
import com.example.movieappapianddesign.adapter.UpcomingMovieAdapter;
import com.example.movieappapianddesign.api.ApiClient;
import com.example.movieappapianddesign.api.ApiInterface;
import com.example.movieappapianddesign.model.Constants;
import com.example.movieappapianddesign.model.PopularMovies;
import com.example.movieappapianddesign.model.UpcomingMovies;
import com.example.movieappapianddesign.ui.login.LoginActivity;
import com.example.movieappapianddesign.ui.login.RegistrationActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ItemOnClickListenerPopular, ItemOnClickListenerUpcoming, NavigationView.OnNavigationItemSelectedListener {
    private ImageView imgRight, imgLeft, imgUser;
    private TextView txvEmailUser, txvChangeAvatar;
    private Button btnLoadMore;

    private RecyclerView recyclerView, recyclerViewUpComing;
    private List<PopularMovies.Results> mListPopular;
    private List<UpcomingMovies.Results> mListUpComing;
    private PopularMovieAdapter popularMovieAdapter;
    private UpcomingMovieAdapter upComingMovieAdapter;

    public static final int PICK_IMAGE = 1;

    private FirebaseAuth auth;

    private ApiInterface apiInterface;
    //
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    //
    int PAGE =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgRight = findViewById(R.id.imgRight);
        imgLeft = findViewById(R.id.imgLeft);
        imgUser = findViewById(R.id.imgUser);
        txvEmailUser = findViewById(R.id.txvEmailUser);
        txvChangeAvatar = findViewById(R.id.txvChangeAvatar);
        btnLoadMore = findViewById(R.id.btnLoadMore);

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        auth = FirebaseAuth.getInstance();

        getMoviesPopular();

        getMoviesUpComing();

        hideShowLeftRight();
        
        //
        drawermenu();
        setNavigationViewListener();

        //event click header
        //clickHeader();//không dc dùng kiểu này
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "image", Toast.LENGTH_SHORT).show();
                Intent intentSelectPhoto = new Intent(Intent.ACTION_PICK);
                intentSelectPhoto.setType("image/*");
                startActivityForResult(intentSelectPhoto, PICK_IMAGE);
            }
        });
        //get Email User
        try {
            String email = auth.getCurrentUser().getEmail();
            txvEmailUser.setText(email);
        }catch (Exception e){

        }


        //check Internet
        checkInternet();

    }


    //check to see if the user is currently signed in
    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

    }


/*    private void clickHeader() {
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "image", Toast.LENGTH_SHORT).show();
            }
        });
        txvEmailUser.setText("text");
    }*/

    private void setNavigationViewListener() {
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        //khai báo để bắt sự kiện trong header
        View headerView = navigationView.getHeaderView(0);
        imgUser = headerView.findViewById(R.id.imgUser);
        txvEmailUser = headerView.findViewById(R.id.txvEmailUser);
        txvChangeAvatar = headerView.findViewById(R.id.txvChangeAvatar);
        //txvLoginWithGoogle = headerView.findViewById(R.id.txvLoginWithGoogle);

    }

    private void drawermenu() {
        drawerLayout = findViewById(R.id.drawerMenu);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        switch (item.getItemId()){
            case R.id.imgUser:
                //Toast.makeText(this, "image", Toast.LENGTH_SHORT).show();
                //open gallery in device
                Intent intentSelectPhoto = new Intent(Intent.ACTION_PICK);
                intentSelectPhoto.setType("image/*");
                startActivityForResult(intentSelectPhoto, PICK_IMAGE);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //select item menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //header nó nằm trong main chứ không phải menu
        switch (menuItem.getItemId()) {
            case R.id.menuRegister:
                Intent intentRegis = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intentRegis);
                break;
            case R.id.menuLogin:
                Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                break;

            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Logout success!", Toast.LENGTH_SHORT).show();
                txvEmailUser.setText("");
                break;
            case R.id.menuContactUs:
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"thanmanhvinh@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                //email.putExtra(Intent.EXTRA_TEXT, "");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client: "));
                default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //Select Image User
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //select image user
        if (requestCode == PICK_IMAGE) {
            //Toast.makeText(this, "Select: "+data, Toast.LENGTH_SHORT).show();
            try {
                if (data != null){//phải check null k là sẽ bị lỗi
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imgUser.setImageBitmap(selectedImage);

                    txvChangeAvatar.setVisibility(View.GONE);
                }else {
                    return;
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void hideShowLeftRight() {
        recyclerViewUpComing.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                //int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                int firstVisible = layoutManager.findFirstVisibleItemPosition();

                if (lastVisible == mListUpComing.size() - 1){
                    imgRight.setVisibility(View.GONE);
                }else {
                    imgRight.setVisibility(View.VISIBLE);
                }

                if (firstVisible == 0){
                    imgLeft.setVisibility(View.GONE);
                }else {
                    imgLeft.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void getMoviesPopular() {
        recyclerView = findViewById(R.id.recyclerView);
        mListPopular = new ArrayList<>();
        popularMovieAdapter = new PopularMovieAdapter(mListPopular, this, this::onClickListenerPopularMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(popularMovieAdapter);

        Call<PopularMovies> call = apiInterface.getMoviesPopular(Constants.KEY_API, PAGE);
        call.enqueue(new Callback<PopularMovies>() {
            @Override
            public void onResponse(Call<PopularMovies> call, Response<PopularMovies> response) {
                if (response.isSuccessful()){
                    if (!mListPopular.isEmpty()){
                        mListPopular.clear();
                    }
                    //add list
                    mListPopular.addAll(response.body().getResults());
                    popularMovieAdapter.notifyDataSetChanged();

                    //Load More
                    btnLoadMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PAGE = PAGE + 1;

                            Call<PopularMovies> calls = apiInterface.getMoviesPopular(Constants.KEY_API, PAGE);
                            calls.enqueue(new Callback<PopularMovies>() {
                                @Override
                                public void onResponse(Call<PopularMovies> call, Response<PopularMovies> response) {
                                    if (response.isSuccessful()) {
                                        //khi load more thì không cần clear nữa nếu k nó sẽ cứ hiện ở vtri cuối cùng
                                        /*if (!mListPopular.isEmpty()) {
                                            mListPopular.clear();
                                        }*/
                                        //add list
                                        mListPopular.addAll(response.body().getResults());
                                        popularMovieAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(Call<PopularMovies> call, Throwable t) {

                                }
                            });

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<PopularMovies> call, Throwable t) {
                //Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMoviesUpComing() {
        recyclerViewUpComing = findViewById(R.id.recyclerViewUpComing);
        mListUpComing = new ArrayList<>();
        upComingMovieAdapter = new UpcomingMovieAdapter(mListUpComing, this, this::onClickListenerUpcoming);
        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewUpComing.setHasFixedSize(true);
        recyclerViewUpComing.setAdapter(upComingMovieAdapter);

        Call<UpcomingMovies> callUpComingMovies = apiInterface.getMoviesUpComing(Constants.KEY_API);
        callUpComingMovies.enqueue(new Callback<UpcomingMovies>() {
            @Override
            public void onResponse(Call<UpcomingMovies> call, Response<UpcomingMovies> response) {
                if (response.isSuccessful()){
                    if (!mListUpComing.isEmpty()){
                        mListUpComing.clear();
                    }
                    //add list
                    mListUpComing.addAll(response.body().getResults());
                    upComingMovieAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<UpcomingMovies> call, Throwable t) {
                //Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClickListenerPopularMovies(int position) {
        PopularMovies.Results moviePopular = mListPopular.get(position);
        //Toast.makeText(this, "Position: "+ position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, MoviesDetailPopularActivity.class);
        intent.putExtra("titlePopular", moviePopular.getTitle());
        intent.putExtra("imagePosterPopular", moviePopular.getBackdropPath());
        intent.putExtra("smallPosterPopular", moviePopular.getPosterPath());
        intent.putExtra("overviewPopular", moviePopular.getOverview());

        startActivity(intent);
    }


    @Override
    public void onClickListenerUpcoming(int positionUp) {
        UpcomingMovies.Results movieUpcoming = mListUpComing.get(positionUp);
        //Toast.makeText(this, "Position: "+ position, Toast.LENGTH_SHORT).show();
        Intent intentUp = new Intent(MainActivity.this, MoviesDetailUpcomingActivity.class);
        intentUp.putExtra("titleUpcoming", movieUpcoming.getTitle());
        intentUp.putExtra("imagePosterUpcoming", movieUpcoming.getBackdropPath());
        intentUp.putExtra("smallPosterUpcoming", movieUpcoming.getPosterPath());
        intentUp.putExtra("overviewUpcoming", movieUpcoming.getOverview());

        startActivity(intentUp);
    }

    private void checkInternet() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
            connected = true;
        }else {
            connected = false;
            Intent intentNotConnect = new Intent(MainActivity.this, NotInternetActivity.class);
            startActivity(intentNotConnect);

        }

    }



//menu right

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuRegister:
                Intent intentRegis = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intentRegis);
                break;
            case R.id.menuLogin:
                Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                break;

            case R.id.menuProfile:
                Intent intentSelectPhoto = new Intent(Intent.ACTION_PICK);
                intentSelectPhoto.setType("image/*");
                startActivityForResult(intentSelectPhoto, PICK_IMAGE);
                break;

            case R.id.menuLogout:
                auth.signOut();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Select image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            //Toast.makeText(this, "Select: "+data, Toast.LENGTH_SHORT).show();
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imgSlectPhoto.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }
    }*/



}
//api key:
//034bbd1b233d6726e0c7dc7f338657f9
//
//URL:
//Popular movie
//https://api.themoviedb.org/3/movie/popular?api_key=034bbd1b233d6726e0c7dc7f338657f9
//Upcoming movie
//https://api.themoviedb.org/3/movie/upcoming?api_key=034bbd1b233d6726e0c7dc7f338657f9
//poster
//https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg