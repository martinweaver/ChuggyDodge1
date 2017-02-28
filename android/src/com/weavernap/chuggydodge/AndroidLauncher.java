package com.weavernap.chuggydodge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.weavernap.cdHelpers.AdsController;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;


public class AndroidLauncher extends AndroidApplication  implements GameHelper.GameHelperListener,
        AdsController, GoogleApiClient.OnConnectionFailedListener{

    private static final int REQUEST_INVITE = 987987;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

 //   private static final String BANNER_AD_UNIT_ID = "ca-app-pub-3737397260010456/7958274520";

    private static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3737397260010456/3314422124";

    protected AdView adView;
    private InterstitialAd interstitialAd;
    protected View gameView;

    private GameHelper gameHelper;
  //  private AdsController adsController;
    private GoogleApiClient mGoogleApiClient;



    private SharedPreferences prefs;
    private boolean writeLogs = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);


        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3737397260010456~9090257326");

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();


        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        AdView adView = this.createAdView();
        relativeLayout.addView(adView);
        relativeLayout.addView(this.createGameView(config));
        this.setContentView(relativeLayout);
      //  this.startAdvertising(adView);
        this.interstitialAd = new InterstitialAd(this);
        this.interstitialAd.setAdUnitId(INTERSTITIAL_AD_UNIT_ID);


        if (this.gameHelper == null) {
            this.gameHelper = new GameHelper(this, 1);
            this.gameHelper.enableDebugLog(this.writeLogs);
        }
        this.gameHelper.setup(this);


//
//		// Create a gameView and a bannerAd AdView
//		View gameView = initializeForView(new CDGame(this), config);
//		setupAds();


//		// Define the layout
//		RelativeLayout layout = new RelativeLayout(this);
//		layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT,
//				ViewGroup.LayoutParams.MATCH_PARENT);
//		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//				ViewGroup.LayoutParams.MATCH_PARENT,
//				ViewGroup.LayoutParams.WRAP_CONTENT);
//		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//		layout.addView(bannerAd, params);
//
//		setContentView(layout);



        // Create an auto-managed GoogleApiClient with access to App Invites.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(AppInvite.API)
              //  .enableAutoManage(this, this)
                .build();

        // Check for App Invite invitations and launch deep-link activity if possible.
        // Requires that an Activity is registered in AndroidManifest.xml to handle
        // deep-link URLs.
        boolean autoLaunchDeepLink = true;
        AppInvite.AppInviteApi.getInvitation(mGoogleApiClient, this, autoLaunchDeepLink)
                .setResultCallback(
                        new ResultCallback<AppInviteInvitationResult>() {
                            @Override
                            public void onResult(AppInviteInvitationResult result) {
                                Log.d(TAG, "getInvitation:onResult:" + result.getStatus());
                                if (result.getStatus().isSuccess()) {
                                    // Extract information from the intent
                                    Intent intent = result.getInvitationIntent();
                                    String deepLink = AppInviteReferral.getDeepLink(intent);
                                    String invitationId = AppInviteReferral.getInvitationId(intent);

                                    // Because autoLaunchDeepLink = true we don't have to do anything
                                    // here, but we could set that to false and manually choose
                                    // an Activity to launch to handle the deep link here.
                                    // ...
                                }
                            }
                        });

    }


    private void onInviteClicked() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
             //   .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
             //   .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        gameHelper.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Log.d(TAG, "onActivityResult: sent invitation " + id);
                }
            } else {
                // Sending failed or it was canceled, show failure message to the user
                Log.d(TAG, "Failed to send invitation.");
            }
        }
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                );
            } else{
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        gameHelper.onStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        gameHelper.onStop();
    }

//    @Override
//    public void onActivityResult(int request, int response, Intent data) {
//        super.onActivityResult(request, response, data);
//        gameHelper.onActivityResult(request, response, data);
//    }


    @Override
    public void showOrLoadInterstitial(final boolean showAd) {
        try {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    if (AndroidLauncher.this.interstitialAd.isLoaded() && showAd) {
                        AndroidLauncher.this.interstitialAd.show();
                        return;
                    } //else
                    {
                        AdRequest adRequest = new AdRequest.Builder().build();
                        AndroidLauncher.this.interstitialAd.loadAd(adRequest);
                    }
                }
            });
            return;
        } catch (Exception e) {
            Gdx.app.log("ChuggerDodge.ERROR", "Exception in showOrLoadInterstitial:" + e.toString());
            return;
        }
    }


    @Override
    public void submitScoreGPGS(int score) {
        if (this.getSignedInGPGS()) {
            Games.Leaderboards.submitScore(this.gameHelper.getApiClient(), this.getString(R.string.leaderboard_top_scores), score);
        }

    }


    @Override
    public void unlockAchievementGPGS(int score) {

        if (this.getSignedInGPGS()) {
            if (score == 0) {
                Games.Achievements.unlock(this.gameHelper.getApiClient(),
                        this.getString(R.string.achievement_the_every_loser_wins_trophy));
            }

            if (score > 3) {
                Games.Achievements.unlock(this.gameHelper.getApiClient(),
                        this.getString(R.string.achievement_not_completely_useless));
            }

            if (score > 19) {
                Games.Achievements.unlock(this.gameHelper.getApiClient(),
                        this.getString(R.string.achievement_hey_youre_all_right_you_are));
            }

            if (score == 42) {
                Games.Achievements.unlock(this.gameHelper.getApiClient(),
                        this.getString(R.string.achievement_the_hyperintelligent_pandimensional_being_prize));
            }

            if (score > 43) {
                Games.Achievements.unlock(this.gameHelper.getApiClient(),
                        this.getString(R.string.achievement_wowzers_youre_about_as_good_as_me_now));
            }


            if (score > 76) {
                Games.Achievements.unlock(this.gameHelper.getApiClient(),
                        this.getString(R.string.achievement_actually_thats_quite_impressive));
            }

            if (score == 100) {
                Games.Achievements.unlock(this.gameHelper.getApiClient(),
                        this.getString(R.string.achievement_the_kenzie_100_prize));
            }


        }


    }


    @Override
    public void getGPGSLeaderboard() {
        if (this.gameHelper.isSignedIn()) {
            this.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(this.gameHelper.getApiClient(),
                    this.getString(R.string.leaderboard_top_scores)), 100);
            return;
        } else {
            if (this.gameHelper.isConnecting()) return;
            {
                this.loginGPGS();
                return;
            }
        }
    }

    @Override
    public boolean getSignedInGPGS() {
        if (this.gameHelper != null) {
            System.out.println("Superduperdavid");
            return this.gameHelper.isSignedIn();
        }
        System.out.println("Nah");
        return false;
    }

//


    @Override
    public void loginGPGS() {
        //  if (!gameHelper.isSignedIn()) {
        try {
            runOnUiThread(new Runnable() {
                public void run() {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
            return;
        } catch (final Exception ex) {
            Gdx.app.log("MainActivity", "Log in failed: " + ex.getMessage() + ".");
            return;
        }
//
    }
//

    @Override
    public void getAchievementsGPGS() {
        //     if (gameHelper.isSignedIn()) {
        startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 101);
//        } else if (!gameHelper.isConnecting()) {
//            loginGPGS();
//        }
    }

    @Override
    public void getOnInviteClicked() {
        onInviteClicked();
    }


    //Following from toaster code

    private AdView createAdView() {
        this.adView = new AdView(this);
     //   this.adView.setAdSize(AdSize.SMART_BANNER);
     //   this.adView.setAdUnitId(BANNER_AD_UNIT_ID);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(12, -1);
        layoutParams.addRule(14, -1);
        this.adView.setLayoutParams(layoutParams);
        this.adView.setBackgroundColor(Color.TRANSPARENT);
        return this.adView;
    }

    private View createGameView(AndroidApplicationConfiguration androidApplicationConfiguration) {
        this.gameView = this.initializeForView(new CDGame(this), androidApplicationConfiguration);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(10, -1);
        layoutParams.addRule(14, -1);
        layoutParams.addRule(2, this.adView.getId());
        this.gameView.setLayoutParams(layoutParams);
        return this.gameView;
    }

//    private void startAdvertising(AdView adView) {
//        adView.loadAd(new AdRequest.Builder().build());
//    }


    @Override
    public void onDestroy() {
        if (this.adView != null) {
            this.adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        if (this.adView != null) {
            this.adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.adView != null) {
            this.adView.resume();
        }
    }


    @Override
    public void onSignInFailed() {
        gameHelper.getSignInError();

    }

    @Override
    public void onSignInSucceeded() {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
}


//Other tut


//	public void setupAds() {
//		bannerAd = new AdView(this);
//		bannerAd.setVisibility(View.INVISIBLE);
//		bannerAd.setBackgroundColor(Color.TRANSPARENT);
//		bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
//		bannerAd.setAdSize(AdSize.SMART_BANNER);
//
//		interstitialAd = new InterstitialAd(this);
//		interstitialAd.setAdUnitId(INTERSTITIAL_AD_UNIT_ID);
//
//		AdRequest.Builder builder = new AdRequest.Builder();
//		AdRequest ad = builder.build();
//		interstitialAd.loadAd(ad);
//	}

//	@Override
//	public void showBannerAd() {
//		runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				bannerAd.setVisibility(View.VISIBLE);
//				AdRequest.Builder builder = new AdRequest.Builder();
//				AdRequest ad = builder.build();
//				bannerAd.loadAd(ad);
//			}
//		});
//	}
//
//	@Override
//	public void hideBannerAd() {
//		runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				bannerAd.setVisibility(View.INVISIBLE);
//			}
//		});
//	}
