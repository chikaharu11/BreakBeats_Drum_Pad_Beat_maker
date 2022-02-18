package jp.chikaharu11.instant_drumpad_breakbeats

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.PorterDuff
import android.media.*
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import jp.chikaharu11.instant_drumpad_breakbeats.databinding.ActivityMainBinding
import java.util.*
import kotlin.math.hypot


class MainActivity : AppCompatActivity(), CustomAdapterListener {

    private var mRewardedAd: RewardedAd? = null

    private lateinit var binding: ActivityMainBinding

    private lateinit var adViewContainer: FrameLayout
    private lateinit var admobmAdView: AdView

    private var mpDuration = 429
    private var mpDuration2 = 429
    private var mpDuration3 = 414
    private var mpDuration4 = 429
    private var mpDuration5 = 429
    private var mpDuration6 = 429
    private var mpDuration7 = 1846
    private var mpDuration8 = 1846
    private var mpDuration9 = 1846
    private var mpDuration10 = 1846
    private var mpDuration11 = 1846
    private var mpDuration12 = 1846
    private var mpDuration13 = 1846
    private var mpDuration14 = 1846
    private var mpDuration15 = 1846

    private var actionTitle = "break_a_01".replace("_"," ").uppercase() + " loop"
    private var padText1 = "b_snare_01".replace("_"," ").uppercase()
    private var padText2 = "b_snare_02".replace("_"," ").uppercase()
    private var padText3 = "b_tamb_01".replace("_"," ").uppercase()
    private var padText4 = "b_tamb_02".replace("_"," ").uppercase()
    private var padText5 = "b_kick_01".replace("_"," ").uppercase()
    private var padText6 = "b_kick_02".replace("_"," ").uppercase()
    private var padText7 = "break_a_01".replace("_"," ").uppercase()
    private var padText8 = "break_a_02".replace("_"," ").uppercase()
    private var padText9 = "break_a_03".replace("_"," ").uppercase()
    private var padText10 = "break_a_04".replace("_"," ").uppercase()
    private var padText11 = "break_a_05".replace("_"," ").uppercase()
    private var padText12 = "break_a_06".replace("_"," ").uppercase()
    private var padText13 = "breakfx_a_01".replace("_"," ").uppercase()
    private var padText14 = "breakfx_a_02".replace("_"," ").uppercase()
    private var padText15 = "breakfx_a_03".replace("_"," ").uppercase()

    private var count = 0.5f
    private var bpm = 1.0f

    private var soundPoolVolume = 0.5f
    private var soundPoolTempo = 1.0f
    private var soundPoolVolume2 = 0.5f
    private var soundPoolTempo2 = 1.0f
    private var soundPoolVolume3 = 0.5f
    private var soundPoolTempo3 = 1.0f
    private var soundPoolVolume4 = 0.5f
    private var soundPoolTempo4 = 1.0f
    private var soundPoolVolume5 = 0.5f
    private var soundPoolTempo5 = 1.0f
    private var soundPoolVolume6 = 0.5f
    private var soundPoolTempo6 = 1.0f
    private var soundPoolVolume7 = 0.5f
    private var soundPoolTempo7 = 1.0f
    private var soundPoolVolume8 = 0.5f
    private var soundPoolTempo8 = 1.0f
    private var soundPoolVolume9 = 0.5f
    private var soundPoolTempo9 = 1.0f
    private var soundPoolVolume10 = 0.5f
    private var soundPoolTempo10 = 1.0f
    private var soundPoolVolume11 = 0.5f
    private var soundPoolTempo11 = 1.0f
    private var soundPoolVolume12 = 0.5f
    private var soundPoolTempo12 = 1.0f
    private var soundPoolVolume13 = 0.5f
    private var soundPoolTempo13 = 1.0f
    private var soundPoolVolume14 = 0.5f
    private var soundPoolTempo14 = 1.0f
    private var soundPoolVolume15 = 0.5f
    private var soundPoolTempo15 = 1.0f

    private val locale: Locale = Locale.getDefault()

    companion object {
        private const val READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 41
        private const val RECORD_AUDIO_PERMISSION_REQUEST_CODE = 42
    }

    @SuppressLint("Range")
    fun selectEX() {
        if (!isReadExternalStoragePermissionGranted()) {
            requestReadExternalStoragePermission()
        } else {
            tSoundList.clear()
            val audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val cursor = contentResolver.query(audioUri, null, null, null, null)
            cursor!!.moveToFirst()
            val path: Array<String?> = arrayOfNulls(cursor.count)
            for (i in path.indices) {
                path[i] = cursor.getString(cursor.getColumnIndex("_data"))
                tSoundList.add(SoundList(path[i].toString()))
                cursor.moveToNext()
            }

            cursor.close()
        }
    }

    private lateinit var soundPool: SoundPool

    private lateinit var mp: MediaPlayer

    private lateinit var getmpDuration: MediaPlayer

    private lateinit var lmp: LoopMediaPlayer

    private lateinit var aCustomAdapter: CustomAdapter
    private lateinit var bCustomAdapter: CustomAdapter
    private lateinit var cCustomAdapter: CustomAdapter
    private lateinit var dCustomAdapter: CustomAdapter
    private lateinit var eCustomAdapter: CustomAdapter

    private lateinit var nCustomAdapter: CustomAdapter
    private lateinit var oCustomAdapter: CustomAdapter
    private lateinit var pCustomAdapter: CustomAdapter
    private lateinit var qCustomAdapter: CustomAdapter

    private lateinit var sCustomAdapter: CustomAdapter
    private lateinit var tCustomAdapter: CustomAdapter

    private lateinit var aSoundList: MutableList<SoundList>
    private lateinit var bSoundList: MutableList<SoundList>
    private lateinit var cSoundList: MutableList<SoundList>
    private lateinit var dSoundList: MutableList<SoundList>
    private lateinit var eSoundList: MutableList<SoundList>

    private lateinit var nSoundList: MutableList<SoundList>
    private lateinit var oSoundList: MutableList<SoundList>
    private lateinit var pSoundList: MutableList<SoundList>
    private lateinit var qSoundList: MutableList<SoundList>

    private lateinit var sSoundList: MutableList<SoundList>
    private lateinit var tSoundList: MutableList<SoundList>

    private var sound1 = 0
    private var sound2 = 0
    private var sound3 = 0
    private var sound4 = 0
    private var sound5 = 0
    private var sound6 = 0
    private var sound7 = 0
    private var sound8 = 0
    private var sound9 = 0
    private var sound10 = 0
    private var sound11 = 0
    private var sound12 = 0
    private var sound13 = 0
    private var sound14 = 0
    private var sound15 = 0
    private var sound16 = 0

    private var paste = 0

    private var buttonA = 0
    private var buttonB = 0

    private var adCheck = 0


    @SuppressLint("ClickableViewAccessibility", "SetTextI18n", "Range", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
            .apply { setContentView(this.root) }

        stickyImmersiveMode()
        initAdMob()
        loadAdMob()
        loadRewardedAd()

        val orientation = resources.configuration.orientation

                binding.textView.text = padText1
                binding.textView2.text = padText2
                binding.textView3.text = padText3
                binding.textView4.text = padText4
                binding.textView5.text = padText5
                binding.textView6.text = padText6
                binding.textView7.text = padText7
                binding.textView8.text = padText8
                binding.textView9.text = padText9
                binding.textView10.text = padText10
                binding.textView11.text = padText11
                binding.textView12.text = padText12
                binding.textView13.text = padText13
                binding.textView14.text = padText14
                binding.textView15.text = padText15
                findViewById<TextView>(R.id.padText0).text = "loop"
        findViewById<View>(R.id.include_view).findViewById<TextView>(R.id.padText).text = padText1
        findViewById<View>(R.id.include_view2).findViewById<TextView>(R.id.padText).text = padText2
        findViewById<View>(R.id.include_view3).findViewById<TextView>(R.id.padText).text = padText3
        findViewById<View>(R.id.include_view4).findViewById<TextView>(R.id.padText).text = padText4
        findViewById<View>(R.id.include_view5).findViewById<TextView>(R.id.padText).text = padText5
        findViewById<View>(R.id.include_view6).findViewById<TextView>(R.id.padText).text = padText6
        findViewById<View>(R.id.include_view7).findViewById<TextView>(R.id.padText).text = padText7
        findViewById<View>(R.id.include_view8).findViewById<TextView>(R.id.padText).text = padText8
        findViewById<View>(R.id.include_view9).findViewById<TextView>(R.id.padText).text = padText9
        findViewById<View>(R.id.include_view10).findViewById<TextView>(R.id.padText).text = padText10
        findViewById<View>(R.id.include_view11).findViewById<TextView>(R.id.padText).text = padText11
        findViewById<View>(R.id.include_view12).findViewById<TextView>(R.id.padText).text = padText12
        findViewById<View>(R.id.include_view13).findViewById<TextView>(R.id.padText).text = padText13
        findViewById<View>(R.id.include_view14).findViewById<TextView>(R.id.padText).text = padText14
        findViewById<View>(R.id.include_view15).findViewById<TextView>(R.id.padText).text = padText15

        val tuning = if (locale == Locale.JAPAN) {
            arrayOf(
                "サウンドの調整",
                "サウンドの設定をリセット",
                "バナー広告を非表示にする",
                "終了する",
                "5x3","5x2","5x1",
                "4x3","4x2","4x1",
                "3x3","3x2","3x1",
                "2x2","2x1"
            ) } else {
            arrayOf(
                "Adjusting Sounds",
                "Reset the sound settings",
                "Hide banner Ads",
                "EXIT",
                "5x3","5x2","5x1",
                "4x3","4x2","4x1",
                "3x3","3x2","3x1",
                "2x2","2x1"
            )
        }
        val adapter = ArrayAdapter(this, R.layout.custom_spinner_dropdown, tuning)
        val gridView: GridView = findViewById(R.id.grid_view)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { adapterView, _, position, _ ->
            when(adapterView.getItemAtPosition(position)) {
                "サウンドの調整" -> {
                    binding.view.visibility = View.VISIBLE
                    gridView.visibility = View.INVISIBLE
                }
                "サウンドの設定をリセット" -> {
                    count = 0.5f
                    bpm = 1.0f
                    soundPoolVolume = 0.5f
                    soundPoolTempo = 1.0f
                    soundPoolVolume2 = 0.5f
                    soundPoolTempo2 = 1.0f
                    soundPoolVolume3 = 0.5f
                    soundPoolTempo3 = 1.0f
                    soundPoolVolume4 = 0.5f
                    soundPoolTempo4 = 1.0f
                    soundPoolVolume5 = 0.5f
                    soundPoolTempo5 = 1.0f
                    soundPoolVolume6 = 0.5f
                    soundPoolTempo6 = 1.0f
                    soundPoolVolume7 = 0.5f
                    soundPoolTempo7 = 1.0f
                    soundPoolVolume8 = 0.5f
                    soundPoolTempo8 = 1.0f
                    soundPoolVolume9 = 0.5f
                    soundPoolTempo9 = 1.0f
                    soundPoolVolume10 = 0.5f
                    soundPoolTempo10 = 1.0f
                    soundPoolVolume11 = 0.5f
                    soundPoolTempo11 = 1.0f
                    soundPoolVolume12 = 0.5f
                    soundPoolTempo12 = 1.0f
                    soundPoolVolume13 = 0.5f
                    soundPoolTempo13 = 1.0f
                    soundPoolVolume14 = 0.5f
                    soundPoolTempo14 = 1.0f
                    soundPoolVolume15 = 0.5f
                    soundPoolTempo15 = 1.0f
                    findViewById<View>(R.id.include_view).findViewById<TextView>(
                        R.id.padText).text = padText1
                    findViewById<View>(R.id.include_view2).findViewById<TextView>(
                        R.id.padText).text = padText2
                    findViewById<View>(R.id.include_view3).findViewById<TextView>(
                        R.id.padText).text = padText3
                    findViewById<View>(R.id.include_view4).findViewById<TextView>(
                        R.id.padText).text = padText4
                    findViewById<View>(R.id.include_view5).findViewById<TextView>(
                        R.id.padText).text = padText5
                    findViewById<View>(R.id.include_view6).findViewById<TextView>(
                        R.id.padText).text = padText6
                    findViewById<View>(R.id.include_view7).findViewById<TextView>(
                        R.id.padText).text = padText7
                    findViewById<View>(R.id.include_view8).findViewById<TextView>(
                        R.id.padText).text = padText8
                    findViewById<View>(R.id.include_view9).findViewById<TextView>(
                        R.id.padText).text = padText9
                    findViewById<View>(R.id.include_view10).findViewById<TextView>(
                        R.id.padText).text = padText10
                    findViewById<View>(R.id.include_view11).findViewById<TextView>(
                        R.id.padText).text = padText11
                    findViewById<View>(R.id.include_view12).findViewById<TextView>(
                        R.id.padText).text = padText12
                    findViewById<View>(R.id.include_view13).findViewById<TextView>(
                        R.id.padText).text = padText13
                    findViewById<View>(R.id.include_view14).findViewById<TextView>(
                        R.id.padText).text = padText14
                    findViewById<View>(R.id.include_view15).findViewById<TextView>(
                        R.id.padText).text = padText15
                    binding.gridView.visibility = View.INVISIBLE
                    Toast.makeText(applicationContext, R.string.reset1, Toast.LENGTH_LONG).show()
                }
                "バナー広告を非表示にする" -> {
                    if (adCheck == 0) {
                        AlertDialog.Builder(this)
                            .setOnCancelListener {
                                stickyImmersiveMode()
                            }
                            .setTitle(R.string.menu5a)
                            .setMessage(R.string.menu5b)
                            .setPositiveButton("YES") { _, _ ->
                                showRewardAd()
                            }
                            .setNegativeButton("NO") { _, _ ->
                                stickyImmersiveMode()
                            }
                            .show()
                    } else if (adCheck == 1){
                        AlertDialog.Builder(this)
                            .setOnCancelListener {
                                stickyImmersiveMode()
                            }
                            .setTitle(R.string.menu5c)
                            .setPositiveButton("OK") { _, _ ->
                                stickyImmersiveMode()
                            }
                            .show()
                    }
                }
                "終了する" -> {
                    AlertDialog.Builder(this)
                        .setOnCancelListener {
                            stickyImmersiveMode()
                        }
                        .setTitle(R.string.menu6)
                        .setPositiveButton("YES") { _, _ ->
                            finish()
                        }
                        .setNegativeButton("NO") { _, _ ->
                            stickyImmersiveMode()
                        }
                        .show()
                }
                "Adjusting Sounds" -> {
                    binding.view.visibility = View.VISIBLE
                    gridView.visibility = View.INVISIBLE
                }
                "Reset the sound settings" -> {
                    soundPoolVolume = 0.5f
                    soundPoolTempo = 1.0f
                    soundPoolVolume2 = 0.5f
                    soundPoolTempo2 = 1.0f
                    soundPoolVolume3 = 0.5f
                    soundPoolTempo3 = 1.0f
                    soundPoolVolume4 = 0.5f
                    soundPoolTempo4 = 1.0f
                    soundPoolVolume5 = 0.5f
                    soundPoolTempo5 = 1.0f
                    soundPoolVolume6 = 0.5f
                    soundPoolTempo6 = 1.0f
                    soundPoolVolume7 = 0.5f
                    soundPoolTempo7 = 1.0f
                    soundPoolVolume8 = 0.5f
                    soundPoolTempo8 = 1.0f
                    soundPoolVolume9 = 0.5f
                    soundPoolTempo9 = 1.0f
                    soundPoolVolume10 = 0.5f
                    soundPoolTempo10 = 1.0f
                    soundPoolVolume11 = 0.5f
                    soundPoolTempo11 = 1.0f
                    soundPoolVolume12 = 0.5f
                    soundPoolTempo12 = 1.0f
                    soundPoolVolume13 = 0.5f
                    soundPoolTempo13 = 1.0f
                    soundPoolVolume14 = 0.5f
                    soundPoolTempo14 = 1.0f
                    soundPoolVolume15 = 0.5f
                    soundPoolTempo15 = 1.0f
                    findViewById<View>(R.id.include_view).findViewById<TextView>(
                        R.id.padText).text = padText1
                    findViewById<View>(R.id.include_view2).findViewById<TextView>(
                        R.id.padText).text = padText2
                    findViewById<View>(R.id.include_view3).findViewById<TextView>(
                        R.id.padText).text = padText3
                    findViewById<View>(R.id.include_view4).findViewById<TextView>(
                        R.id.padText).text = padText4
                    findViewById<View>(R.id.include_view5).findViewById<TextView>(
                        R.id.padText).text = padText5
                    findViewById<View>(R.id.include_view6).findViewById<TextView>(
                        R.id.padText).text = padText6
                    findViewById<View>(R.id.include_view7).findViewById<TextView>(
                        R.id.padText).text = padText7
                    findViewById<View>(R.id.include_view8).findViewById<TextView>(
                        R.id.padText).text = padText8
                    findViewById<View>(R.id.include_view9).findViewById<TextView>(
                        R.id.padText).text = padText9
                    findViewById<View>(R.id.include_view10).findViewById<TextView>(
                        R.id.padText).text = padText10
                    findViewById<View>(R.id.include_view11).findViewById<TextView>(
                        R.id.padText).text = padText11
                    findViewById<View>(R.id.include_view12).findViewById<TextView>(
                        R.id.padText).text = padText12
                    findViewById<View>(R.id.include_view13).findViewById<TextView>(
                        R.id.padText).text = padText13
                    findViewById<View>(R.id.include_view14).findViewById<TextView>(
                        R.id.padText).text = padText14
                    findViewById<View>(R.id.include_view15).findViewById<TextView>(
                        R.id.padText).text = padText15
                    binding.gridView.visibility = View.INVISIBLE
                    Toast.makeText(applicationContext, R.string.reset1, Toast.LENGTH_LONG).show()
                }
                "Hide banner Ads" -> {
                    if (adCheck == 0) {
                        AlertDialog.Builder(this)
                            .setOnCancelListener {
                                stickyImmersiveMode()
                            }
                            .setTitle(R.string.menu5a)
                            .setMessage(R.string.menu5b)
                            .setPositiveButton("YES") { _, _ ->
                                showRewardAd()
                            }
                            .setNegativeButton("NO") { _, _ ->
                                stickyImmersiveMode()
                            }
                            .show()
                    } else if (adCheck == 1){
                        AlertDialog.Builder(this)
                            .setOnCancelListener {
                                stickyImmersiveMode()
                            }
                            .setTitle(R.string.menu5c)
                            .setPositiveButton("OK") { _, _ ->
                                stickyImmersiveMode()
                            }
                            .show()
                    }
                }
                "EXIT" -> {
                    AlertDialog.Builder(this)
                        .setOnCancelListener {
                            stickyImmersiveMode()
                        }
                        .setTitle(R.string.menu6)
                        .setPositiveButton("YES") { _, _ ->
                            finish()
                        }
                        .setNegativeButton("NO") { _, _ ->
                            stickyImmersiveMode()
                        }
                        .show()
                }
                "5x3" -> {
                    binding.imageView2.visibility = View.VISIBLE
                    binding.imageView5.visibility = View.VISIBLE
                    binding.imageView3.visibility = View.VISIBLE
                    binding.imageView6.visibility = View.VISIBLE
                    binding.imageView7.visibility = View.VISIBLE
                    binding.imageView8.visibility = View.VISIBLE
                    binding.imageView9.visibility = View.VISIBLE
                    binding.imageView10.visibility = View.VISIBLE
                    binding.imageView11.visibility = View.VISIBLE
                    binding.imageView12.visibility = View.VISIBLE
                    binding.imageView13.visibility = View.VISIBLE
                    binding.imageView14.visibility = View.VISIBLE
                    binding.imageView15.visibility = View.VISIBLE
                    binding.backgroundView2.visibility = View.VISIBLE
                    binding.backgroundView5.visibility = View.VISIBLE
                    binding.backgroundView3.visibility = View.VISIBLE
                    binding.backgroundView6.visibility = View.VISIBLE
                    binding.backgroundView7.visibility = View.VISIBLE
                    binding.backgroundView8.visibility = View.VISIBLE
                    binding.backgroundView9.visibility = View.VISIBLE
                    binding.backgroundView10.visibility = View.VISIBLE
                    binding.backgroundView11.visibility = View.VISIBLE
                    binding.backgroundView12.visibility = View.VISIBLE
                    binding.backgroundView13.visibility = View.VISIBLE
                    binding.backgroundView14.visibility = View.VISIBLE
                    binding.backgroundView15.visibility = View.VISIBLE
                    binding.textView2.visibility = View.VISIBLE
                    binding.textView5.visibility = View.VISIBLE
                    binding.textView3.visibility = View.VISIBLE
                    binding.textView6.visibility = View.VISIBLE
                    binding.textView7.visibility = View.VISIBLE
                    binding.textView8.visibility = View.VISIBLE
                    binding.textView9.visibility = View.VISIBLE
                    binding.textView10.visibility = View.VISIBLE
                    binding.textView11.visibility = View.VISIBLE
                    binding.textView12.visibility = View.VISIBLE
                    binding.textView13.visibility = View.VISIBLE
                    binding.textView14.visibility = View.VISIBLE
                    binding.textView15.visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view2).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view5).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view3).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view6).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view7).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view8).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view9).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view10).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view11).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view12).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view13).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view14).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view15).visibility = View.VISIBLE
                    binding.gridView.visibility = View.INVISIBLE
                }
                "4x3" -> {
                    binding.imageView2.visibility = View.VISIBLE
                    binding.imageView5.visibility = View.VISIBLE
                    binding.imageView3.visibility = View.VISIBLE
                    binding.imageView6.visibility = View.VISIBLE
                    binding.imageView7.visibility = View.VISIBLE
                    binding.imageView8.visibility = View.VISIBLE
                    binding.imageView9.visibility = View.VISIBLE
                    binding.imageView10.visibility = View.VISIBLE
                    binding.imageView11.visibility = View.VISIBLE
                    binding.imageView12.visibility = View.VISIBLE
                    binding.imageView13.visibility = View.GONE
                    binding.imageView14.visibility = View.GONE
                    binding.imageView15.visibility = View.GONE
                    binding.backgroundView2.visibility = View.VISIBLE
                    binding.backgroundView5.visibility = View.VISIBLE
                    binding.backgroundView3.visibility = View.VISIBLE
                    binding.backgroundView6.visibility = View.VISIBLE
                    binding.backgroundView7.visibility = View.VISIBLE
                    binding.backgroundView8.visibility = View.VISIBLE
                    binding.backgroundView9.visibility = View.VISIBLE
                    binding.backgroundView10.visibility = View.VISIBLE
                    binding.backgroundView11.visibility = View.VISIBLE
                    binding.backgroundView12.visibility = View.VISIBLE
                    binding.backgroundView13.visibility = View.GONE
                    binding.backgroundView14.visibility = View.GONE
                    binding.backgroundView15.visibility = View.GONE
                    binding.textView2.visibility = View.VISIBLE
                    binding.textView5.visibility = View.VISIBLE
                    binding.textView3.visibility = View.VISIBLE
                    binding.textView6.visibility = View.VISIBLE
                    binding.textView7.visibility = View.VISIBLE
                    binding.textView8.visibility = View.VISIBLE
                    binding.textView9.visibility = View.VISIBLE
                    binding.textView10.visibility = View.VISIBLE
                    binding.textView11.visibility = View.VISIBLE
                    binding.textView12.visibility = View.VISIBLE
                    binding.textView13.visibility = View.GONE
                    binding.textView14.visibility = View.GONE
                    binding.textView15.visibility = View.GONE
                    findViewById<View>(R.id.include_view2).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view5).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view3).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view6).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view7).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view8).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view9).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view10).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view11).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view12).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view13).visibility = View.GONE
                    findViewById<View>(R.id.include_view14).visibility = View.GONE
                    findViewById<View>(R.id.include_view15).visibility = View.GONE
                    binding.gridView.visibility = View.INVISIBLE
                }
                "3x3" -> {
                    binding.imageView2.visibility = View.VISIBLE
                    binding.imageView5.visibility = View.VISIBLE
                    binding.imageView3.visibility = View.VISIBLE
                    binding.imageView6.visibility = View.VISIBLE
                    binding.imageView7.visibility = View.VISIBLE
                    binding.imageView8.visibility = View.VISIBLE
                    binding.imageView9.visibility = View.VISIBLE
                    binding.imageView10.visibility = View.GONE
                    binding.imageView11.visibility = View.GONE
                    binding.imageView12.visibility = View.GONE
                    binding.imageView13.visibility = View.GONE
                    binding.imageView14.visibility = View.GONE
                    binding.imageView15.visibility = View.GONE
                    binding.backgroundView2.visibility = View.VISIBLE
                    binding.backgroundView5.visibility = View.VISIBLE
                    binding.backgroundView3.visibility = View.VISIBLE
                    binding.backgroundView6.visibility = View.VISIBLE
                    binding.backgroundView7.visibility = View.VISIBLE
                    binding.backgroundView8.visibility = View.VISIBLE
                    binding.backgroundView9.visibility = View.VISIBLE
                    binding.backgroundView10.visibility = View.GONE
                    binding.backgroundView11.visibility = View.GONE
                    binding.backgroundView12.visibility = View.GONE
                    binding.backgroundView13.visibility = View.GONE
                    binding.backgroundView14.visibility = View.GONE
                    binding.backgroundView15.visibility = View.GONE
                    binding.textView2.visibility = View.VISIBLE
                    binding.textView5.visibility = View.VISIBLE
                    binding.textView3.visibility = View.VISIBLE
                    binding.textView6.visibility = View.VISIBLE
                    binding.textView7.visibility = View.VISIBLE
                    binding.textView8.visibility = View.VISIBLE
                    binding.textView9.visibility = View.VISIBLE
                    binding.textView10.visibility = View.GONE
                    binding.textView11.visibility = View.GONE
                    binding.textView12.visibility = View.GONE
                    binding.textView13.visibility = View.GONE
                    binding.textView14.visibility = View.GONE
                    binding.textView15.visibility = View.GONE
                    findViewById<View>(R.id.include_view2).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view5).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view3).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view6).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view7).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view8).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view9).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view10).visibility = View.GONE
                    findViewById<View>(R.id.include_view11).visibility = View.GONE
                    findViewById<View>(R.id.include_view12).visibility = View.GONE
                    findViewById<View>(R.id.include_view13).visibility = View.GONE
                    findViewById<View>(R.id.include_view14).visibility = View.GONE
                    findViewById<View>(R.id.include_view15).visibility = View.GONE
                    binding.gridView.visibility = View.INVISIBLE
                }
                "5x2" -> {
                    binding.imageView2.visibility = View.VISIBLE
                    binding.imageView5.visibility = View.VISIBLE
                    binding.imageView7.visibility = View.VISIBLE
                    binding.imageView8.visibility = View.VISIBLE
                    binding.imageView10.visibility = View.VISIBLE
                    binding.imageView11.visibility = View.VISIBLE
                    binding.imageView13.visibility = View.VISIBLE
                    binding.imageView14.visibility = View.VISIBLE
                    binding.imageView3.visibility = View.GONE
                    binding.imageView6.visibility = View.GONE
                    binding.imageView9.visibility = View.GONE
                    binding.imageView12.visibility = View.GONE
                    binding.imageView15.visibility = View.GONE
                    binding.backgroundView2.visibility = View.VISIBLE
                    binding.backgroundView5.visibility = View.VISIBLE
                    binding.backgroundView7.visibility = View.VISIBLE
                    binding.backgroundView8.visibility = View.VISIBLE
                    binding.backgroundView10.visibility = View.VISIBLE
                    binding.backgroundView11.visibility = View.VISIBLE
                    binding.backgroundView13.visibility = View.VISIBLE
                    binding.backgroundView14.visibility = View.VISIBLE
                    binding.backgroundView3.visibility = View.GONE
                    binding.backgroundView6.visibility = View.GONE
                    binding.backgroundView9.visibility = View.GONE
                    binding.backgroundView12.visibility = View.GONE
                    binding.backgroundView15.visibility = View.GONE
                    binding.textView2.visibility = View.VISIBLE
                    binding.textView5.visibility = View.VISIBLE
                    binding.textView7.visibility = View.VISIBLE
                    binding.textView8.visibility = View.VISIBLE
                    binding.textView10.visibility = View.VISIBLE
                    binding.textView11.visibility = View.VISIBLE
                    binding.textView13.visibility = View.VISIBLE
                    binding.textView14.visibility = View.VISIBLE
                    binding.textView3.visibility = View.GONE
                    binding.textView6.visibility = View.GONE
                    binding.textView9.visibility = View.GONE
                    binding.textView12.visibility = View.GONE
                    binding.textView15.visibility = View.GONE
                    findViewById<View>(R.id.include_view2).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view5).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view7).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view8).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view10).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view11).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view13).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view14).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view3).visibility = View.GONE
                    findViewById<View>(R.id.include_view6).visibility = View.GONE
                    findViewById<View>(R.id.include_view9).visibility = View.GONE
                    findViewById<View>(R.id.include_view12).visibility = View.GONE
                    findViewById<View>(R.id.include_view15).visibility = View.GONE
                    binding.gridView.visibility = View.INVISIBLE
                }
                "4x2" -> {
                    binding.imageView2.visibility = View.VISIBLE
                    binding.imageView5.visibility = View.VISIBLE
                    binding.imageView7.visibility = View.VISIBLE
                    binding.imageView8.visibility = View.VISIBLE
                    binding.imageView10.visibility = View.VISIBLE
                    binding.imageView11.visibility = View.VISIBLE
                    binding.imageView3.visibility = View.GONE
                    binding.imageView6.visibility = View.GONE
                    binding.imageView9.visibility = View.GONE
                    binding.imageView12.visibility = View.GONE
                    binding.imageView13.visibility = View.GONE
                    binding.imageView14.visibility = View.GONE
                    binding.imageView15.visibility = View.GONE
                    binding.backgroundView2.visibility = View.VISIBLE
                    binding.backgroundView5.visibility = View.VISIBLE
                    binding.backgroundView7.visibility = View.VISIBLE
                    binding.backgroundView8.visibility = View.VISIBLE
                    binding.backgroundView10.visibility = View.VISIBLE
                    binding.backgroundView11.visibility = View.VISIBLE
                    binding.backgroundView3.visibility = View.GONE
                    binding.backgroundView6.visibility = View.GONE
                    binding.backgroundView9.visibility = View.GONE
                    binding.backgroundView12.visibility = View.GONE
                    binding.backgroundView13.visibility = View.GONE
                    binding.backgroundView14.visibility = View.GONE
                    binding.backgroundView15.visibility = View.GONE
                    binding.textView2.visibility = View.VISIBLE
                    binding.textView5.visibility = View.VISIBLE
                    binding.textView7.visibility = View.VISIBLE
                    binding.textView8.visibility = View.VISIBLE
                    binding.textView10.visibility = View.VISIBLE
                    binding.textView11.visibility = View.VISIBLE
                    binding.textView3.visibility = View.GONE
                    binding.textView6.visibility = View.GONE
                    binding.textView9.visibility = View.GONE
                    binding.textView12.visibility = View.GONE
                    binding.textView13.visibility = View.GONE
                    binding.textView14.visibility = View.GONE
                    binding.textView15.visibility = View.GONE
                    findViewById<View>(R.id.include_view2).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view5).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view7).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view8).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view10).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view11).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view3).visibility = View.GONE
                    findViewById<View>(R.id.include_view6).visibility = View.GONE
                    findViewById<View>(R.id.include_view9).visibility = View.GONE
                    findViewById<View>(R.id.include_view12).visibility = View.GONE
                    findViewById<View>(R.id.include_view13).visibility = View.GONE
                    findViewById<View>(R.id.include_view14).visibility = View.GONE
                    findViewById<View>(R.id.include_view15).visibility = View.GONE
                    binding.gridView.visibility = View.INVISIBLE
                }
                "3x2" -> {
                    binding.imageView2.visibility = View.VISIBLE
                    binding.imageView5.visibility = View.VISIBLE
                    binding.imageView7.visibility = View.VISIBLE
                    binding.imageView8.visibility = View.VISIBLE
                    binding.imageView3.visibility = View.GONE
                    binding.imageView6.visibility = View.GONE
                    binding.imageView9.visibility = View.GONE
                    binding.imageView10.visibility = View.GONE
                    binding.imageView11.visibility = View.GONE
                    binding.imageView12.visibility = View.GONE
                    binding.imageView13.visibility = View.GONE
                    binding.imageView14.visibility = View.GONE
                    binding.imageView15.visibility = View.GONE
                    binding.backgroundView2.visibility = View.VISIBLE
                    binding.backgroundView5.visibility = View.VISIBLE
                    binding.backgroundView7.visibility = View.VISIBLE
                    binding.backgroundView8.visibility = View.VISIBLE
                    binding.backgroundView3.visibility = View.GONE
                    binding.backgroundView6.visibility = View.GONE
                    binding.backgroundView9.visibility = View.GONE
                    binding.backgroundView10.visibility = View.GONE
                    binding.backgroundView11.visibility = View.GONE
                    binding.backgroundView12.visibility = View.GONE
                    binding.backgroundView13.visibility = View.GONE
                    binding.backgroundView14.visibility = View.GONE
                    binding.backgroundView15.visibility = View.GONE
                    binding.textView2.visibility = View.VISIBLE
                    binding.textView5.visibility = View.VISIBLE
                    binding.textView7.visibility = View.VISIBLE
                    binding.textView8.visibility = View.VISIBLE
                    binding.textView3.visibility = View.GONE
                    binding.textView6.visibility = View.GONE
                    binding.textView9.visibility = View.GONE
                    binding.textView10.visibility = View.GONE
                    binding.textView11.visibility = View.GONE
                    binding.textView12.visibility = View.GONE
                    binding.textView13.visibility = View.GONE
                    binding.textView14.visibility = View.GONE
                    binding.textView15.visibility = View.GONE
                    findViewById<View>(R.id.include_view2).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view5).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view7).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view8).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view3).visibility = View.GONE
                    findViewById<View>(R.id.include_view6).visibility = View.GONE
                    findViewById<View>(R.id.include_view9).visibility = View.GONE
                    findViewById<View>(R.id.include_view10).visibility = View.GONE
                    findViewById<View>(R.id.include_view11).visibility = View.GONE
                    findViewById<View>(R.id.include_view12).visibility = View.GONE
                    findViewById<View>(R.id.include_view13).visibility = View.GONE
                    findViewById<View>(R.id.include_view14).visibility = View.GONE
                    findViewById<View>(R.id.include_view15).visibility = View.GONE
                    binding.gridView.visibility = View.INVISIBLE
                }
                "2x2" -> {
                    binding.imageView2.visibility = View.VISIBLE
                    binding.imageView5.visibility = View.VISIBLE
                    binding.imageView3.visibility = View.GONE
                    binding.imageView6.visibility = View.GONE
                    binding.imageView7.visibility = View.GONE
                    binding.imageView8.visibility = View.GONE
                    binding.imageView9.visibility = View.GONE
                    binding.imageView10.visibility = View.GONE
                    binding.imageView11.visibility = View.GONE
                    binding.imageView12.visibility = View.GONE
                    binding.imageView13.visibility = View.GONE
                    binding.imageView14.visibility = View.GONE
                    binding.imageView15.visibility = View.GONE
                    binding.backgroundView2.visibility = View.VISIBLE
                    binding.backgroundView5.visibility = View.VISIBLE
                    binding.backgroundView3.visibility = View.GONE
                    binding.backgroundView6.visibility = View.GONE
                    binding.backgroundView7.visibility = View.GONE
                    binding.backgroundView8.visibility = View.GONE
                    binding.backgroundView9.visibility = View.GONE
                    binding.backgroundView10.visibility = View.GONE
                    binding.backgroundView11.visibility = View.GONE
                    binding.backgroundView12.visibility = View.GONE
                    binding.backgroundView13.visibility = View.GONE
                    binding.backgroundView14.visibility = View.GONE
                    binding.backgroundView15.visibility = View.GONE
                    binding.textView2.visibility = View.VISIBLE
                    binding.textView5.visibility = View.VISIBLE
                    binding.textView3.visibility = View.GONE
                    binding.textView6.visibility = View.GONE
                    binding.textView7.visibility = View.GONE
                    binding.textView8.visibility = View.GONE
                    binding.textView9.visibility = View.GONE
                    binding.textView10.visibility = View.GONE
                    binding.textView11.visibility = View.GONE
                    binding.textView12.visibility = View.GONE
                    binding.textView13.visibility = View.GONE
                    binding.textView14.visibility = View.GONE
                    binding.textView15.visibility = View.GONE
                    findViewById<View>(R.id.include_view2).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view5).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view3).visibility = View.GONE
                    findViewById<View>(R.id.include_view6).visibility = View.GONE
                    findViewById<View>(R.id.include_view7).visibility = View.GONE
                    findViewById<View>(R.id.include_view8).visibility = View.GONE
                    findViewById<View>(R.id.include_view9).visibility = View.GONE
                    findViewById<View>(R.id.include_view10).visibility = View.GONE
                    findViewById<View>(R.id.include_view11).visibility = View.GONE
                    findViewById<View>(R.id.include_view12).visibility = View.GONE
                    findViewById<View>(R.id.include_view13).visibility = View.GONE
                    findViewById<View>(R.id.include_view14).visibility = View.GONE
                    findViewById<View>(R.id.include_view15).visibility = View.GONE
                    binding.gridView.visibility = View.INVISIBLE
                }
                "2x1" -> {
                    binding.imageView2.visibility = View.GONE
                    binding.imageView3.visibility = View.GONE
                    binding.imageView5.visibility = View.GONE
                    binding.imageView6.visibility = View.GONE
                    binding.imageView7.visibility = View.GONE
                    binding.imageView8.visibility = View.GONE
                    binding.imageView9.visibility = View.GONE
                    binding.imageView10.visibility = View.GONE
                    binding.imageView11.visibility = View.GONE
                    binding.imageView12.visibility = View.GONE
                    binding.imageView13.visibility = View.GONE
                    binding.imageView14.visibility = View.GONE
                    binding.imageView15.visibility = View.GONE
                    binding.backgroundView2.visibility = View.GONE
                    binding.backgroundView3.visibility = View.GONE
                    binding.backgroundView5.visibility = View.GONE
                    binding.backgroundView6.visibility = View.GONE
                    binding.backgroundView7.visibility = View.GONE
                    binding.backgroundView8.visibility = View.GONE
                    binding.backgroundView9.visibility = View.GONE
                    binding.backgroundView10.visibility = View.GONE
                    binding.backgroundView11.visibility = View.GONE
                    binding.backgroundView12.visibility = View.GONE
                    binding.backgroundView13.visibility = View.GONE
                    binding.backgroundView14.visibility = View.GONE
                    binding.backgroundView15.visibility = View.GONE
                    binding.textView2.visibility = View.GONE
                    binding.textView3.visibility = View.GONE
                    binding.textView5.visibility = View.GONE
                    binding.textView6.visibility = View.GONE
                    binding.textView7.visibility = View.GONE
                    binding.textView8.visibility = View.GONE
                    binding.textView9.visibility = View.GONE
                    binding.textView10.visibility = View.GONE
                    binding.textView11.visibility = View.GONE
                    binding.textView12.visibility = View.GONE
                    binding.textView13.visibility = View.GONE
                    binding.textView14.visibility = View.GONE
                    binding.textView15.visibility = View.GONE
                    findViewById<View>(R.id.include_view2).visibility = View.GONE
                    findViewById<View>(R.id.include_view3).visibility = View.GONE
                    findViewById<View>(R.id.include_view5).visibility = View.GONE
                    findViewById<View>(R.id.include_view6).visibility = View.GONE
                    findViewById<View>(R.id.include_view7).visibility = View.GONE
                    findViewById<View>(R.id.include_view8).visibility = View.GONE
                    findViewById<View>(R.id.include_view9).visibility = View.GONE
                    findViewById<View>(R.id.include_view10).visibility = View.GONE
                    findViewById<View>(R.id.include_view11).visibility = View.GONE
                    findViewById<View>(R.id.include_view12).visibility = View.GONE
                    findViewById<View>(R.id.include_view13).visibility = View.GONE
                    findViewById<View>(R.id.include_view14).visibility = View.GONE
                    findViewById<View>(R.id.include_view15).visibility = View.GONE
                    binding.gridView.visibility = View.INVISIBLE
                }
                "5x1" -> {
                    binding.imageView7.visibility = View.VISIBLE
                    binding.imageView10.visibility = View.VISIBLE
                    binding.imageView13.visibility = View.VISIBLE
                    binding.imageView2.visibility = View.GONE
                    binding.imageView3.visibility = View.GONE
                    binding.imageView5.visibility = View.GONE
                    binding.imageView6.visibility = View.GONE
                    binding.imageView8.visibility = View.GONE
                    binding.imageView9.visibility = View.GONE
                    binding.imageView11.visibility = View.GONE
                    binding.imageView12.visibility = View.GONE
                    binding.imageView14.visibility = View.GONE
                    binding.imageView15.visibility = View.GONE
                    binding.backgroundView7.visibility = View.VISIBLE
                    binding.backgroundView10.visibility = View.VISIBLE
                    binding.backgroundView13.visibility = View.VISIBLE
                    binding.backgroundView2.visibility = View.GONE
                    binding.backgroundView3.visibility = View.GONE
                    binding.backgroundView5.visibility = View.GONE
                    binding.backgroundView6.visibility = View.GONE
                    binding.backgroundView8.visibility = View.GONE
                    binding.backgroundView9.visibility = View.GONE
                    binding.backgroundView11.visibility = View.GONE
                    binding.backgroundView12.visibility = View.GONE
                    binding.backgroundView14.visibility = View.GONE
                    binding.backgroundView15.visibility = View.GONE
                    binding.textView7.visibility = View.VISIBLE
                    binding.textView10.visibility = View.VISIBLE
                    binding.textView13.visibility = View.VISIBLE
                    binding.textView2.visibility = View.GONE
                    binding.textView3.visibility = View.GONE
                    binding.textView5.visibility = View.GONE
                    binding.textView6.visibility = View.GONE
                    binding.textView8.visibility = View.GONE
                    binding.textView9.visibility = View.GONE
                    binding.textView11.visibility = View.GONE
                    binding.textView12.visibility = View.GONE
                    binding.textView14.visibility = View.GONE
                    binding.textView15.visibility = View.GONE
                    findViewById<View>(R.id.include_view7).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view10).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view13).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view2).visibility = View.GONE
                    findViewById<View>(R.id.include_view3).visibility = View.GONE
                    findViewById<View>(R.id.include_view5).visibility = View.GONE
                    findViewById<View>(R.id.include_view6).visibility = View.GONE
                    findViewById<View>(R.id.include_view8).visibility = View.GONE
                    findViewById<View>(R.id.include_view9).visibility = View.GONE
                    findViewById<View>(R.id.include_view11).visibility = View.GONE
                    findViewById<View>(R.id.include_view12).visibility = View.GONE
                    findViewById<View>(R.id.include_view14).visibility = View.GONE
                    findViewById<View>(R.id.include_view15).visibility = View.GONE
                    binding.gridView.visibility = View.INVISIBLE
                }
                "4x1" -> {
                    binding.imageView7.visibility = View.VISIBLE
                    binding.imageView10.visibility = View.VISIBLE
                    binding.imageView2.visibility = View.GONE
                    binding.imageView3.visibility = View.GONE
                    binding.imageView5.visibility = View.GONE
                    binding.imageView6.visibility = View.GONE
                    binding.imageView8.visibility = View.GONE
                    binding.imageView9.visibility = View.GONE
                    binding.imageView11.visibility = View.GONE
                    binding.imageView12.visibility = View.GONE
                    binding.imageView13.visibility = View.GONE
                    binding.imageView14.visibility = View.GONE
                    binding.imageView15.visibility = View.GONE
                    binding.backgroundView7.visibility = View.VISIBLE
                    binding.backgroundView10.visibility = View.VISIBLE
                    binding.backgroundView2.visibility = View.GONE
                    binding.backgroundView3.visibility = View.GONE
                    binding.backgroundView5.visibility = View.GONE
                    binding.backgroundView6.visibility = View.GONE
                    binding.backgroundView8.visibility = View.GONE
                    binding.backgroundView9.visibility = View.GONE
                    binding.backgroundView11.visibility = View.GONE
                    binding.backgroundView12.visibility = View.GONE
                    binding.backgroundView13.visibility = View.GONE
                    binding.backgroundView14.visibility = View.GONE
                    binding.backgroundView15.visibility = View.GONE
                    binding.textView7.visibility = View.VISIBLE
                    binding.textView10.visibility = View.VISIBLE
                    binding.textView2.visibility = View.GONE
                    binding.textView3.visibility = View.GONE
                    binding.textView5.visibility = View.GONE
                    binding.textView6.visibility = View.GONE
                    binding.textView8.visibility = View.GONE
                    binding.textView9.visibility = View.GONE
                    binding.textView11.visibility = View.GONE
                    binding.textView12.visibility = View.GONE
                    binding.textView13.visibility = View.GONE
                    binding.textView14.visibility = View.GONE
                    binding.textView15.visibility = View.GONE
                    findViewById<View>(R.id.include_view7).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view10).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view2).visibility = View.GONE
                    findViewById<View>(R.id.include_view3).visibility = View.GONE
                    findViewById<View>(R.id.include_view5).visibility = View.GONE
                    findViewById<View>(R.id.include_view6).visibility = View.GONE
                    findViewById<View>(R.id.include_view8).visibility = View.GONE
                    findViewById<View>(R.id.include_view9).visibility = View.GONE
                    findViewById<View>(R.id.include_view11).visibility = View.GONE
                    findViewById<View>(R.id.include_view12).visibility = View.GONE
                    findViewById<View>(R.id.include_view13).visibility = View.GONE
                    findViewById<View>(R.id.include_view14).visibility = View.GONE
                    findViewById<View>(R.id.include_view15).visibility = View.GONE
                    binding.gridView.visibility = View.INVISIBLE
                }
                "3x1" -> {
                    binding.imageView7.visibility = View.VISIBLE
                    binding.imageView2.visibility = View.GONE
                    binding.imageView3.visibility = View.GONE
                    binding.imageView5.visibility = View.GONE
                    binding.imageView6.visibility = View.GONE
                    binding.imageView8.visibility = View.GONE
                    binding.imageView9.visibility = View.GONE
                    binding.imageView10.visibility = View.GONE
                    binding.imageView11.visibility = View.GONE
                    binding.imageView12.visibility = View.GONE
                    binding.imageView13.visibility = View.GONE
                    binding.imageView14.visibility = View.GONE
                    binding.imageView15.visibility = View.GONE
                    binding.backgroundView7.visibility = View.VISIBLE
                    binding.backgroundView2.visibility = View.GONE
                    binding.backgroundView3.visibility = View.GONE
                    binding.backgroundView5.visibility = View.GONE
                    binding.backgroundView6.visibility = View.GONE
                    binding.backgroundView8.visibility = View.GONE
                    binding.backgroundView9.visibility = View.GONE
                    binding.backgroundView10.visibility = View.GONE
                    binding.backgroundView11.visibility = View.GONE
                    binding.backgroundView12.visibility = View.GONE
                    binding.backgroundView13.visibility = View.GONE
                    binding.backgroundView14.visibility = View.GONE
                    binding.backgroundView15.visibility = View.GONE
                    binding.textView7.visibility = View.VISIBLE
                    binding.textView2.visibility = View.GONE
                    binding.textView3.visibility = View.GONE
                    binding.textView5.visibility = View.GONE
                    binding.textView6.visibility = View.GONE
                    binding.textView8.visibility = View.GONE
                    binding.textView9.visibility = View.GONE
                    binding.textView10.visibility = View.GONE
                    binding.textView11.visibility = View.GONE
                    binding.textView12.visibility = View.GONE
                    binding.textView13.visibility = View.GONE
                    binding.textView14.visibility = View.GONE
                    binding.textView15.visibility = View.GONE
                    findViewById<View>(R.id.include_view7).visibility = View.VISIBLE
                    findViewById<View>(R.id.include_view2).visibility = View.GONE
                    findViewById<View>(R.id.include_view3).visibility = View.GONE
                    findViewById<View>(R.id.include_view5).visibility = View.GONE
                    findViewById<View>(R.id.include_view6).visibility = View.GONE
                    findViewById<View>(R.id.include_view8).visibility = View.GONE
                    findViewById<View>(R.id.include_view9).visibility = View.GONE
                    findViewById<View>(R.id.include_view10).visibility = View.GONE
                    findViewById<View>(R.id.include_view11).visibility = View.GONE
                    findViewById<View>(R.id.include_view12).visibility = View.GONE
                    findViewById<View>(R.id.include_view13).visibility = View.GONE
                    findViewById<View>(R.id.include_view14).visibility = View.GONE
                    findViewById<View>(R.id.include_view15).visibility = View.GONE
                    binding.gridView.visibility = View.INVISIBLE
                }
            }
        }

        val choose = if (locale == Locale.JAPAN) {
            arrayOf(
                "パッド音の変更",
                "メトロノーム・ループ",
                "125bpm・ループ",
                "130bpm・ループ",
                "135bpm・ループ",
                "140bpm・ループ",
                "外部サウンド・ループ",
                "ドラムパッドをリセット"
            ) } else {
            arrayOf(
                "Change Pad Sounds",
                "Metronome Loops",
                "125bpm Loops",
                "130bpm Loops",
                "135bpm Loops",
                "140bpm Loops",
                "External sound Loops",
                "Reset drum pads",
            )
        }
        val choose2 = if (locale == Locale.JAPAN) {
            arrayOf(
                "プレイモードに変更",
                "メトロノーム・ループ",
                "125bpm・ループ",
                "130bpm・ループ",
                "135bpm・ループ",
                "140bpm・ループ",
                "外部サウンド・ループ",
                "ドラムパッドをリセット"
            ) } else {
            arrayOf(
                "Change to Play Mode",
                "Metronome Loops",
                "125bpm Loops",
                "130bpm Loops",
                "135bpm Loops",
                "140bpm Loops",
                "External sound Loops",
                "Reset drum pads",
            )
        }
        val adapter2 = ArrayAdapter(this, R.layout.custom_spinner_dropdown, choose)
        val adapter2a = ArrayAdapter(this, R.layout.custom_spinner_dropdown, choose2)
        val gridView2: GridView = findViewById(R.id.grid_view_choose)
        val soundListView = findViewById<ListView>(R.id.list_view)
        gridView2.adapter = adapter2

        gridView2.setOnItemClickListener { adapterView, _, position, _ ->
            when (adapterView.getItemAtPosition(position)) {
                "パッド音の変更" -> {
                    paste = 1
                    invalidateOptionsMenu()
                    Toast.makeText(applicationContext, R.string.change, Toast.LENGTH_LONG).show()
                    gridView2.visibility = View.INVISIBLE
                    gridView2.adapter = adapter2a
                    adapter2a.notifyDataSetChanged()
                }
                "プレイモードに変更" -> {
                    paste = 0
                    invalidateOptionsMenu()
                    Toast.makeText(applicationContext, R.string.change2, Toast.LENGTH_LONG).show()
                    gridView2.visibility = View.INVISIBLE
                    gridView2.adapter = adapter2
                    adapter2.notifyDataSetChanged()
                }
                "Change Pad Sounds" -> {
                    paste = 1
                    invalidateOptionsMenu()
                    Toast.makeText(applicationContext, R.string.change, Toast.LENGTH_LONG).show()
                    gridView2.visibility = View.INVISIBLE
                    gridView2.adapter = adapter2a
                    adapter2a.notifyDataSetChanged()
                }
                "Change to Play Mode" -> {
                    paste = 0
                    invalidateOptionsMenu()
                    Toast.makeText(applicationContext, R.string.change2, Toast.LENGTH_LONG).show()
                    gridView2.visibility = View.INVISIBLE
                    gridView2.adapter = adapter2
                    adapter2.notifyDataSetChanged()
                }
                "メトロノーム・ループ" -> {
                    lmp.stop()
                    menuSwitch = true
                    invalidateOptionsMenu()
                    switch1 = 2
                    buttonA = 16
                    buttonB = 2
                    soundListView.adapter = eCustomAdapter
                    eCustomAdapter.notifyDataSetChanged()
                    soundListView.visibility = View.VISIBLE
                    gridView2.visibility = View.INVISIBLE
                }
                "125bpm・ループ" -> {
                    lmp.stop()
                    menuSwitch = true
                    invalidateOptionsMenu()
                    switch1 = 2
                    buttonA = 16
                    buttonB = 2
                    soundListView.adapter = aCustomAdapter
                    aCustomAdapter.notifyDataSetChanged()
                    soundListView.visibility = View.VISIBLE
                    gridView2.visibility = View.INVISIBLE
                }
                "130bpm・ループ" -> {
                    lmp.stop()
                    menuSwitch = true
                    invalidateOptionsMenu()
                    switch1 = 2
                    buttonA = 16
                    buttonB = 2
                    soundListView.adapter = bCustomAdapter
                    bCustomAdapter.notifyDataSetChanged()
                    soundListView.visibility = View.VISIBLE
                    gridView2.visibility = View.INVISIBLE
                }
                "135bpm・ループ" -> {
                    lmp.stop()
                    menuSwitch = true
                    invalidateOptionsMenu()
                    switch1 = 2
                    buttonA = 16
                    buttonB = 2
                    soundListView.adapter = cCustomAdapter
                    cCustomAdapter.notifyDataSetChanged()
                    soundListView.visibility = View.VISIBLE
                    gridView2.visibility = View.INVISIBLE
                }
                "140bpm・ループ" -> {
                    lmp.stop()
                    menuSwitch = true
                    invalidateOptionsMenu()
                    switch1 = 2
                    buttonA = 16
                    buttonB = 2
                    soundListView.adapter = dCustomAdapter
                    dCustomAdapter.notifyDataSetChanged()
                    soundListView.visibility = View.VISIBLE
                    gridView2.visibility = View.INVISIBLE
                }
                "外部サウンド・ループ" -> {
                    lmp.stop()
                    menuSwitch = true
                    invalidateOptionsMenu()
                    switch1 = 2
                    buttonA = 16
                    buttonB = 1
                    selectEX()
                    soundListView.adapter = tCustomAdapter
                    tCustomAdapter.notifyDataSetChanged()
                    soundListView.visibility = View.VISIBLE
                    gridView2.visibility = View.INVISIBLE
                }
                "ドラムパッドをリセット" -> {
                    binding.textView.text = ""
                    binding.textView2.text = ""
                    binding.textView3.text = ""
                    binding.textView4.text = ""
                    binding.textView5.text = ""
                    binding.textView6.text = ""
                    binding.textView7.text = ""
                    binding.textView8.text = ""
                    binding.textView9.text = ""
                    binding.textView10.text = ""
                    binding.textView11.text = ""
                    binding.textView12.text = ""
                    binding.textView13.text = ""
                    binding.textView14.text = ""
                    binding.textView15.text = ""
                    findViewById<View>(R.id.include_view).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view2).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view3).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view4).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view5).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view6).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view7).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view8).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view9).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view10).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view11).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view12).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view13).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view14).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view15).findViewById<TextView>(
                        R.id.padText).text = ""
                    padText1 = ""
                    padText2 = ""
                    padText3 = ""
                    padText4 = ""
                    padText5 = ""
                    padText6 = ""
                    padText7 = ""
                    padText8 = ""
                    padText9 = ""
                    padText10 = ""
                    padText11 = ""
                    padText12 = ""
                    padText13 = ""
                    padText14 = ""
                    padText15 = ""
                    mpDuration = 0
                    mpDuration2 = 0
                    mpDuration3 = 0
                    mpDuration4 = 0
                    mpDuration5 = 0
                    mpDuration6 = 0
                    mpDuration7 = 0
                    mpDuration8 = 0
                    mpDuration9 = 0
                    mpDuration10 = 0
                    mpDuration11 = 0
                    mpDuration12 = 0
                    mpDuration13 = 0
                    mpDuration14 = 0
                    mpDuration15 = 0
                    count = 0.5f
                    bpm = 1.0f
                    soundPoolVolume = 0.5f
                    soundPoolTempo = 1.0f
                    soundPoolVolume2 = 0.5f
                    soundPoolTempo2 = 1.0f
                    soundPoolVolume3 = 0.5f
                    soundPoolTempo3 = 1.0f
                    soundPoolVolume4 = 0.5f
                    soundPoolTempo4 = 1.0f
                    soundPoolVolume5 = 0.5f
                    soundPoolTempo5 = 1.0f
                    soundPoolVolume6 = 0.5f
                    soundPoolTempo6 = 1.0f
                    soundPoolVolume7 = 0.5f
                    soundPoolTempo7 = 1.0f
                    soundPoolVolume8 = 0.5f
                    soundPoolTempo8 = 1.0f
                    soundPoolVolume9 = 0.5f
                    soundPoolTempo9 = 1.0f
                    soundPoolVolume10 = 0.5f
                    soundPoolTempo10 = 1.0f
                    soundPoolVolume11 = 0.5f
                    soundPoolTempo11 = 1.0f
                    soundPoolVolume12 = 0.5f
                    soundPoolTempo12 = 1.0f
                    soundPoolVolume13 = 0.5f
                    soundPoolTempo13 = 1.0f
                    soundPoolVolume14 = 0.5f
                    soundPoolTempo14 = 1.0f
                    soundPoolVolume15 = 0.5f
                    soundPoolTempo15 = 1.0f
                    sound1 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound2 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound3 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound4 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound5 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound6 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound7 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound8 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound9 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound10 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound11 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound12 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound13 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound14 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound15 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound16 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    gridView2.visibility = View.INVISIBLE
                    Toast.makeText(applicationContext, R.string.reset2, Toast.LENGTH_LONG).show()
                }
                "Metronome Loops" -> {
                    lmp.stop()
                    menuSwitch = true
                    invalidateOptionsMenu()
                    switch1 = 2
                    buttonA = 16
                    buttonB = 2
                    soundListView.adapter = eCustomAdapter
                    eCustomAdapter.notifyDataSetChanged()
                    soundListView.visibility = View.VISIBLE
                    gridView2.visibility = View.INVISIBLE
                }
                "125bpm Loops" -> {
                    lmp.stop()
                    menuSwitch = true
                    invalidateOptionsMenu()
                    switch1 = 2
                    buttonA = 16
                    buttonB = 2
                    soundListView.adapter = aCustomAdapter
                    aCustomAdapter.notifyDataSetChanged()
                    soundListView.visibility = View.VISIBLE
                    gridView2.visibility = View.INVISIBLE
                }
                "130bpm Loops" -> {
                    lmp.stop()
                    menuSwitch = true
                    invalidateOptionsMenu()
                    switch1 = 2
                    buttonA = 16
                    buttonB = 2
                    soundListView.adapter = bCustomAdapter
                    bCustomAdapter.notifyDataSetChanged()
                    soundListView.visibility = View.VISIBLE
                    gridView2.visibility = View.INVISIBLE
                }
                "135bpm Loops" -> {
                    lmp.stop()
                    menuSwitch = true
                    invalidateOptionsMenu()
                    switch1 = 2
                    buttonA = 16
                    buttonB = 2
                    soundListView.adapter = cCustomAdapter
                    cCustomAdapter.notifyDataSetChanged()
                    soundListView.visibility = View.VISIBLE
                    gridView2.visibility = View.INVISIBLE
                }
                "140bpm Loops" -> {
                    lmp.stop()
                    menuSwitch = true
                    invalidateOptionsMenu()
                    switch1 = 2
                    buttonA = 16
                    buttonB = 2
                    soundListView.adapter = dCustomAdapter
                    dCustomAdapter.notifyDataSetChanged()
                    soundListView.visibility = View.VISIBLE
                    gridView2.visibility = View.INVISIBLE
                }
                "External sound Loops" -> {
                    lmp.stop()
                    menuSwitch = true
                    invalidateOptionsMenu()
                    switch1 = 2
                    buttonA = 16
                    buttonB = 1
                    selectEX()
                    soundListView.adapter = tCustomAdapter
                    tCustomAdapter.notifyDataSetChanged()
                    soundListView.visibility = View.VISIBLE
                    gridView2.visibility = View.INVISIBLE
                }
                "Reset drum pads" -> {
                    binding.textView.text = ""
                    binding.textView2.text = ""
                    binding.textView3.text = ""
                    binding.textView4.text = ""
                    binding.textView5.text = ""
                    binding.textView6.text = ""
                    binding.textView7.text = ""
                    binding.textView8.text = ""
                    binding.textView9.text = ""
                    binding.textView10.text = ""
                    binding.textView11.text = ""
                    binding.textView12.text = ""
                    binding.textView13.text = ""
                    binding.textView14.text = ""
                    binding.textView15.text = ""
                    findViewById<View>(R.id.include_view).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view2).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view3).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view4).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view5).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view6).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view7).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view8).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view9).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view10).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view11).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view12).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view13).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view14).findViewById<TextView>(
                        R.id.padText).text = ""
                    findViewById<View>(R.id.include_view15).findViewById<TextView>(
                        R.id.padText).text = ""
                    padText1 = ""
                    padText2 = ""
                    padText3 = ""
                    padText4 = ""
                    padText5 = ""
                    padText6 = ""
                    padText7 = ""
                    padText8 = ""
                    padText9 = ""
                    padText10 = ""
                    padText11 = ""
                    padText12 = ""
                    padText13 = ""
                    padText14 = ""
                    padText15 = ""
                    mpDuration = 0
                    mpDuration2 = 0
                    mpDuration3 = 0
                    mpDuration4 = 0
                    mpDuration5 = 0
                    mpDuration6 = 0
                    mpDuration7 = 0
                    mpDuration8 = 0
                    mpDuration9 = 0
                    mpDuration10 = 0
                    mpDuration11 = 0
                    mpDuration12 = 0
                    mpDuration13 = 0
                    mpDuration14 = 0
                    mpDuration15 = 0
                    count = 0.5f
                    bpm = 1.0f
                    soundPoolVolume = 0.5f
                    soundPoolTempo = 1.0f
                    soundPoolVolume2 = 0.5f
                    soundPoolTempo2 = 1.0f
                    soundPoolVolume3 = 0.5f
                    soundPoolTempo3 = 1.0f
                    soundPoolVolume4 = 0.5f
                    soundPoolTempo4 = 1.0f
                    soundPoolVolume5 = 0.5f
                    soundPoolTempo5 = 1.0f
                    soundPoolVolume6 = 0.5f
                    soundPoolTempo6 = 1.0f
                    soundPoolVolume7 = 0.5f
                    soundPoolTempo7 = 1.0f
                    soundPoolVolume8 = 0.5f
                    soundPoolTempo8 = 1.0f
                    soundPoolVolume9 = 0.5f
                    soundPoolTempo9 = 1.0f
                    soundPoolVolume10 = 0.5f
                    soundPoolTempo10 = 1.0f
                    soundPoolVolume11 = 0.5f
                    soundPoolTempo11 = 1.0f
                    soundPoolVolume12 = 0.5f
                    soundPoolTempo12 = 1.0f
                    soundPoolVolume13 = 0.5f
                    soundPoolTempo13 = 1.0f
                    soundPoolVolume14 = 0.5f
                    soundPoolTempo14 = 1.0f
                    soundPoolVolume15 = 0.5f
                    soundPoolTempo15 = 1.0f
                    sound1 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound2 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound3 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound4 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound5 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound6 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound7 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound8 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound9 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound10 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound11 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound12 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound13 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound14 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound15 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    sound16 = soundPool.load(assets.openFd("soundless.ogg"), 1)
                    gridView2.visibility = View.INVISIBLE
                    Toast.makeText(applicationContext, R.string.reset2, Toast.LENGTH_LONG).show()
                }
            }
        }


        aSoundList = arrayListOf(
            SoundList("boos_a_01.ogg"),
            SoundList("boos_a_02.ogg"),
            SoundList("boos_a_03.ogg"),
            SoundList("boos_a_04.ogg"),
            SoundList("boos_c_01.ogg"),
            SoundList("boos_c_02.ogg"),
            SoundList("boos_c_03.ogg"),
            SoundList("boos_c_04.ogg"),
            SoundList("boos_c1.ogg"),
            SoundList("boos_c2.ogg"),
            SoundList("boos_c3.ogg"),
            SoundList("boos_e_01.ogg"),
            SoundList("boos_e_02.ogg"),
            SoundList("boos_e_03.ogg"),
            SoundList("boos_e_04.ogg"),
            SoundList("boos_e1.ogg"),
            SoundList("boos_e2.ogg"),
            SoundList("boos_e3.ogg"),
            SoundList("boos_g_01.ogg"),
            SoundList("boos_g_02.ogg"),
            SoundList("boos_g_03.ogg"),
            SoundList("boos_g_04.ogg"),
            SoundList("boos_g_s1.ogg"),
            SoundList("boos_g_s2.ogg"),
            SoundList("boos_g_s3.ogg"),
            SoundList("break_ca_01.ogg"),
            SoundList("break_ca_02.ogg"),
            SoundList("break_ca_03.ogg"),
            SoundList("break_ca_04.ogg"),
            SoundList("break_ca_05.ogg"),
            SoundList("break_ca_06.ogg"),
            SoundList("break_cb_01.ogg"),
            SoundList("break_cb_02.ogg"),
            SoundList("break_cb_03.ogg"),
            SoundList("break_cb_04.ogg"),
            SoundList("break_cb_05.ogg"),
            SoundList("break_cb_06.ogg"),
            SoundList("chirper_a_01.ogg"),
            SoundList("chirper_a_02.ogg"),
            SoundList("chirper_a_03.ogg"),
            SoundList("chirper_a_04.ogg"),
            SoundList("chirper_c_01.ogg"),
            SoundList("chirper_c_02.ogg"),
            SoundList("chirper_c_03.ogg"),
            SoundList("chirper_c_04.ogg"),
            SoundList("chirper_e_01.ogg"),
            SoundList("chirper_e_02.ogg"),
            SoundList("chirper_e_03.ogg"),
            SoundList("chirper_e_04.ogg"),
            SoundList("chirper_g_01.ogg"),
            SoundList("chirper_g_02.ogg"),
            SoundList("chirper_g_03.ogg"),
            SoundList("chirper_g_04.ogg"),
            SoundList("worp_a_01.ogg"),
            SoundList("worp_a_02.ogg"),
            SoundList("worp_a_03.ogg"),
            SoundList("worp_a_04.ogg"),
            SoundList("worp_c_01.ogg"),
            SoundList("worp_c_02.ogg"),
            SoundList("worp_c_03.ogg"),
            SoundList("worp_c_04.ogg"),
            SoundList("worp_e_01.ogg"),
            SoundList("worp_e_02.ogg"),
            SoundList("worp_e_03.ogg"),
            SoundList("worp_e_04.ogg"),
            SoundList("worp_g_01.ogg"),
            SoundList("worp_g_02.ogg"),
            SoundList("worp_g_03.ogg"),
            SoundList("worp_g_04.ogg"),
        )

        bSoundList = arrayListOf(
            SoundList("break_a_01.ogg"),
            SoundList("break_a_02.ogg"),
            SoundList("break_a_03.ogg"),
            SoundList("break_a_04.ogg"),
            SoundList("break_a_05.ogg"),
            SoundList("break_a_06.ogg"),
            SoundList("break_a_07.ogg"),
            SoundList("break_a_08.ogg"),
            SoundList("break_a_09.ogg"),
            SoundList("break_a_10.ogg"),
            SoundList("breakfx_a_01.ogg"),
            SoundList("breakfx_a_02.ogg"),
            SoundList("breakfx_a_03.ogg"),
            SoundList("breakfx_a_04.ogg"),
            SoundList("breakfx_a_05.ogg"),
            SoundList("breakfx_a_06.ogg"),
            SoundList("breakfx_a_07.ogg"),
            SoundList("breakfx_a_08.ogg"),
            SoundList("breakfx_a_09.ogg"),
            SoundList("breakfx_a_10.ogg"),
            SoundList("goingbass_a_01.ogg"),
            SoundList("goingbass_a_02.ogg"),
            SoundList("goingbass_a_03.ogg"),
            SoundList("goingbass_a_04.ogg"),
            SoundList("goingbass_c_01.ogg"),
            SoundList("goingbass_c_02.ogg"),
            SoundList("goingbass_c_03.ogg"),
            SoundList("goingbass_c_04.ogg"),
            SoundList("goingbass_d_01.ogg"),
            SoundList("goingbass_d_02.ogg"),
            SoundList("goingbass_d_03.ogg"),
            SoundList("goingbass_d_04.ogg"),
            SoundList("goingbass_g_01.ogg"),
            SoundList("goingbass_g_02.ogg"),
            SoundList("goingbass_g_03.ogg"),
            SoundList("goingbass_g_04.ogg"),
            SoundList("goingbass_c1.ogg"),
            SoundList("goingbass_c2.ogg"),
            SoundList("goingbass_c3.ogg"),
            SoundList("goingbass_e1.ogg"),
            SoundList("goingbass_e2.ogg"),
            SoundList("goingbass_e3.ogg"),
            SoundList("goingbass_g_s1.ogg"),
            SoundList("goingbass_g_s2.ogg"),
            SoundList("goingbass_g_s3.ogg"),
            SoundList("peel_a_01.ogg"),
            SoundList("peel_a_02.ogg"),
            SoundList("peel_a_03.ogg"),
            SoundList("peel_a_04.ogg"),
            SoundList("peel_c_01.ogg"),
            SoundList("peel_c_02.ogg"),
            SoundList("peel_c_03.ogg"),
            SoundList("peel_c_04.ogg"),
            SoundList("peel_d_01.ogg"),
            SoundList("peel_d_02.ogg"),
            SoundList("peel_d_03.ogg"),
            SoundList("peel_d_04.ogg"),
            SoundList("peel_g_01.ogg"),
            SoundList("peel_g_02.ogg"),
            SoundList("peel_g_03.ogg"),
            SoundList("peel_g_04.ogg"),
            SoundList("spooch_a_01.ogg"),
            SoundList("spooch_a_02.ogg"),
            SoundList("spooch_a_03.ogg"),
            SoundList("spooch_a_04.ogg"),
            SoundList("spooch_a_05.ogg"),
            SoundList("spooch_a_06.ogg"),
            SoundList("spooch_a_07.ogg"),
            SoundList("spooch_a_08.ogg"),
            SoundList("spooch_b_01.ogg"),
            SoundList("spooch_b_02.ogg"),
            SoundList("spooch_b_03.ogg"),
            SoundList("spooch_b_04.ogg"),
            SoundList("spooch_b_05.ogg"),
            SoundList("spooch_b_06.ogg"),
            SoundList("spooch_b_07.ogg"),
            SoundList("spooch_b_08.ogg"),
            SoundList("synjun_a_01.ogg"),
            SoundList("synjun_a_02.ogg"),
            SoundList("synjun_a_03.ogg"),
            SoundList("synjun_a_04.ogg"),
            SoundList("synjun_c_01.ogg"),
            SoundList("synjun_c_02.ogg"),
            SoundList("synjun_c_03.ogg"),
            SoundList("synjun_c_04.ogg"),
            SoundList("synjun_d_01.ogg"),
            SoundList("synjun_d_02.ogg"),
            SoundList("synjun_d_03.ogg"),
            SoundList("synjun_d_04.ogg"),
            SoundList("synjun_g_01.ogg"),
            SoundList("synjun_g_02.ogg"),
            SoundList("synjun_g_03.ogg"),
            SoundList("synjun_g_04.ogg")
        )
        cSoundList = arrayListOf(
            SoundList("break_d_01.ogg"),
            SoundList("break_d_02.ogg"),
            SoundList("break_d_03.ogg"),
            SoundList("break_d_04.ogg"),
            SoundList("break_d_05.ogg"),
            SoundList("break_d_06.ogg"),
            SoundList("break_d_07.ogg"),
            SoundList("break_d_08.ogg"),
            SoundList("break_d_09.ogg"),
            SoundList("break_d_10.ogg"),
            SoundList("breakfx_d_01.ogg"),
            SoundList("breakfx_d_02.ogg"),
            SoundList("breakfx_d_03.ogg"),
            SoundList("breakfx_d_04.ogg"),
            SoundList("breakfx_d_05.ogg"),
            SoundList("breakfx_d_06.ogg"),
            SoundList("breakfx_d_07.ogg"),
            SoundList("breakfx_d_08.ogg"),
            SoundList("breakfx_d_09.ogg"),
            SoundList("breakfx_d_10.ogg"),
            SoundList("gnatter_a_01.ogg"),
            SoundList("gnatter_a_02.ogg"),
            SoundList("gnatter_a_03.ogg"),
            SoundList("gnatter_a_04.ogg"),
            SoundList("gnatter_c_01.ogg"),
            SoundList("gnatter_c_02.ogg"),
            SoundList("gnatter_c_03.ogg"),
            SoundList("gnatter_c_04.ogg"),
            SoundList("gnatter_e_01.ogg"),
            SoundList("gnatter_e_02.ogg"),
            SoundList("gnatter_e_03.ogg"),
            SoundList("gnatter_e_04.ogg"),
            SoundList("gnatter_g_01.ogg"),
            SoundList("gnatter_g_02.ogg"),
            SoundList("gnatter_g_03.ogg"),
            SoundList("gnatter_g_04.ogg"),
            SoundList("seerpad_a_01.ogg"),
            SoundList("seerpad_a_02.ogg"),
            SoundList("seerpad_a_03.ogg"),
            SoundList("seerpad_a_04.ogg"),
            SoundList("seerpad_c_01.ogg"),
            SoundList("seerpad_c_02.ogg"),
            SoundList("seerpad_c_03.ogg"),
            SoundList("seerpad_c_04.ogg"),
            SoundList("seerpad_e_01.ogg"),
            SoundList("seerpad_e_02.ogg"),
            SoundList("seerpad_e_03.ogg"),
            SoundList("seerpad_e_04.ogg"),
            SoundList("seerpad_g_01.ogg"),
            SoundList("seerpad_g_02.ogg"),
            SoundList("seerpad_g_03.ogg"),
            SoundList("seerpad_g_04.ogg"),
            SoundList("skbass_a_01.ogg"),
            SoundList("skbass_a_02.ogg"),
            SoundList("skbass_a_03.ogg"),
            SoundList("skbass_a_04.ogg"),
            SoundList("skbass_c_01.ogg"),
            SoundList("skbass_c_02.ogg"),
            SoundList("skbass_c_03.ogg"),
            SoundList("skbass_c_04.ogg"),
            SoundList("skbass_c1.ogg"),
            SoundList("skbass_c2.ogg"),
            SoundList("skbass_c3.ogg"),
            SoundList("skbass_e_01.ogg"),
            SoundList("skbass_e_02.ogg"),
            SoundList("skbass_e_03.ogg"),
            SoundList("skbass_e_04.ogg"),
            SoundList("skbass_e1.ogg"),
            SoundList("skbass_e2.ogg"),
            SoundList("skbass_e3.ogg"),
            SoundList("skbass_g_01.ogg"),
            SoundList("skbass_g_02.ogg"),
            SoundList("skbass_g_03.ogg"),
            SoundList("skbass_g_04.ogg"),
            SoundList("skbass_g_s1.ogg"),
            SoundList("skbass_g_s2.ogg"),
            SoundList("skbass_g_s3.ogg")
        )
        dSoundList = arrayListOf(
            SoundList("bludge_a_01.ogg"),
            SoundList("bludge_a_02.ogg"),
            SoundList("bludge_a_03.ogg"),
            SoundList("bludge_a_04.ogg"),
            SoundList("bludge_c_01.ogg"),
            SoundList("bludge_c_02.ogg"),
            SoundList("bludge_c_03.ogg"),
            SoundList("bludge_c_04.ogg"),
            SoundList("bludge_e_01.ogg"),
            SoundList("bludge_e_02.ogg"),
            SoundList("bludge_e_03.ogg"),
            SoundList("bludge_e_04.ogg"),
            SoundList("bludge_g_01.ogg"),
            SoundList("bludge_g_02.ogg"),
            SoundList("bludge_g_03.ogg"),
            SoundList("bludge_g_04.ogg"),
            SoundList("bludge_c1.ogg"),
            SoundList("bludge_c2.ogg"),
            SoundList("bludge_c3.ogg"),
            SoundList("bludge_e1.ogg"),
            SoundList("bludge_e2.ogg"),
            SoundList("bludge_e3.ogg"),
            SoundList("bludge_g_s1.ogg"),
            SoundList("bludge_g_s2.ogg"),
            SoundList("bludge_g_s3.ogg"),
            SoundList("break_b_01.ogg"),
            SoundList("break_b_02.ogg"),
            SoundList("break_b_03.ogg"),
            SoundList("break_b_04.ogg"),
            SoundList("break_b_05.ogg"),
            SoundList("break_b_06.ogg"),
            SoundList("break_b_07.ogg"),
            SoundList("break_b_08.ogg"),
            SoundList("break_b_09.ogg"),
            SoundList("break_b_10.ogg"),
            SoundList("halema_a_01.ogg"),
            SoundList("halema_a_02.ogg"),
            SoundList("halema_a_03.ogg"),
            SoundList("halema_c_01.ogg"),
            SoundList("halema_c_02.ogg"),
            SoundList("halema_c_03.ogg"),
            SoundList("halema_e_01.ogg"),
            SoundList("halema_e_02.ogg"),
            SoundList("halema_e_03.ogg"),
            SoundList("halema_g_01.ogg"),
            SoundList("halema_g_02.ogg"),
            SoundList("halema_g_03.ogg"),
            SoundList("thereman_a_01.ogg"),
            SoundList("thereman_a_02.ogg"),
            SoundList("thereman_a_03.ogg"),
            SoundList("thereman_a_04.ogg"),
            SoundList("thereman_c_01.ogg"),
            SoundList("thereman_c_02.ogg"),
            SoundList("thereman_c_03.ogg"),
            SoundList("thereman_c_04.ogg"),
            SoundList("thereman_e_01.ogg"),
            SoundList("thereman_e_02.ogg"),
            SoundList("thereman_e_03.ogg"),
            SoundList("thereman_e_04.ogg"),
            SoundList("thereman_g_01.ogg"),
            SoundList("thereman_g_02.ogg"),
            SoundList("thereman_g_03.ogg"),
            SoundList("thereman_g_04.ogg")
        )

        eSoundList = arrayListOf(
            SoundList("b_bongo.ogg"),
            SoundList("b_clhat_01.ogg"),
            SoundList("b_clhat_02.ogg"),
            SoundList("b_conga_01.ogg"),
            SoundList("b_conga_02.ogg"),
            SoundList("b_conga_03.ogg"),
            SoundList("b_fizz_01.ogg"),
            SoundList("b_fizz_02.ogg"),
            SoundList("b_fizz_03.ogg"),
            SoundList("b_kick_01.ogg"),
            SoundList("b_kick_02.ogg"),
            SoundList("b_ophat_02.ogg"),
            SoundList("b_ophat01.ogg"),
            SoundList("b_ride_01.ogg"),
            SoundList("b_ride_02.ogg"),
            SoundList("b_ride_03.ogg"),
            SoundList("b_shaker.ogg"),
            SoundList("b_snare_01.ogg"),
            SoundList("b_snare_02.ogg"),
            SoundList("b_snare_03.ogg"),
            SoundList("b_tamb_01.ogg"),
            SoundList("b_tamb_02.ogg"),
            SoundList("b_tamb_03.ogg"),
            SoundList("b_woodblock.ogg"),
            SoundList("b_woodblock.ogg"),
            SoundList("ca_clhat01.ogg"),
            SoundList("ca_clhat02.ogg"),
            SoundList("ca_kick01.ogg"),
            SoundList("ca_kick02.ogg"),
            SoundList("ca_ophat01.ogg"),
            SoundList("ca_ophat02.ogg"),
            SoundList("ca_snare01.ogg"),
            SoundList("ca_snare02.ogg"),
            SoundList("ca_tamb01.ogg"),
            SoundList("ca_tamb02.ogg"),
            SoundList("cb_clhat01.ogg"),
            SoundList("cb_clhat02.ogg"),
            SoundList("cb_kick01.ogg"),
            SoundList("cb_kick02.ogg"),
            SoundList("cb_ophat01.ogg"),
            SoundList("cb_ophat02.ogg"),
            SoundList("cb_snare01.ogg"),
            SoundList("cb_snare02.ogg"),
            SoundList("d_clhat01.ogg"),
            SoundList("d_clhat02.ogg"),
            SoundList("d_crash.ogg"),
            SoundList("d_kick01.ogg"),
            SoundList("d_kick02.ogg"),
            SoundList("d_ophat.ogg"),
            SoundList("d_ride01.ogg"),
            SoundList("d_ride02.ogg"),
            SoundList("d_ridebell.ogg"),
            SoundList("d_snare01.ogg"),
            SoundList("d_snare02.ogg")
        )

        nSoundList = arrayListOf(
            SoundList("boos_a_01.ogg"),
            SoundList("boos_a_02.ogg"),
            SoundList("boos_a_03.ogg"),
            SoundList("boos_a_04.ogg"),
            SoundList("boos_c_01.ogg"),
            SoundList("boos_c_02.ogg"),
            SoundList("boos_c_03.ogg"),
            SoundList("boos_c_04.ogg"),
            SoundList("boos_c1.ogg"),
            SoundList("boos_c2.ogg"),
            SoundList("boos_c3.ogg"),
            SoundList("boos_e_01.ogg"),
            SoundList("boos_e_02.ogg"),
            SoundList("boos_e_03.ogg"),
            SoundList("boos_e_04.ogg"),
            SoundList("boos_e1.ogg"),
            SoundList("boos_e2.ogg"),
            SoundList("boos_e3.ogg"),
            SoundList("boos_g_01.ogg"),
            SoundList("boos_g_02.ogg"),
            SoundList("boos_g_03.ogg"),
            SoundList("boos_g_04.ogg"),
            SoundList("boos_g_s1.ogg"),
            SoundList("boos_g_s2.ogg"),
            SoundList("boos_g_s3.ogg"),
            SoundList("break_ca_01.ogg"),
            SoundList("break_ca_02.ogg"),
            SoundList("break_ca_03.ogg"),
            SoundList("break_ca_04.ogg"),
            SoundList("break_ca_05.ogg"),
            SoundList("break_ca_06.ogg"),
            SoundList("break_cb_01.ogg"),
            SoundList("break_cb_02.ogg"),
            SoundList("break_cb_03.ogg"),
            SoundList("break_cb_04.ogg"),
            SoundList("break_cb_05.ogg"),
            SoundList("break_cb_06.ogg"),
            SoundList("chirper_a_01.ogg"),
            SoundList("chirper_a_02.ogg"),
            SoundList("chirper_a_03.ogg"),
            SoundList("chirper_a_04.ogg"),
            SoundList("chirper_c_01.ogg"),
            SoundList("chirper_c_02.ogg"),
            SoundList("chirper_c_03.ogg"),
            SoundList("chirper_c_04.ogg"),
            SoundList("chirper_e_01.ogg"),
            SoundList("chirper_e_02.ogg"),
            SoundList("chirper_e_03.ogg"),
            SoundList("chirper_e_04.ogg"),
            SoundList("chirper_g_01.ogg"),
            SoundList("chirper_g_02.ogg"),
            SoundList("chirper_g_03.ogg"),
            SoundList("chirper_g_04.ogg"),
            SoundList("worp_a_01.ogg"),
            SoundList("worp_a_02.ogg"),
            SoundList("worp_a_03.ogg"),
            SoundList("worp_a_04.ogg"),
            SoundList("worp_c_01.ogg"),
            SoundList("worp_c_02.ogg"),
            SoundList("worp_c_03.ogg"),
            SoundList("worp_c_04.ogg"),
            SoundList("worp_e_01.ogg"),
            SoundList("worp_e_02.ogg"),
            SoundList("worp_e_03.ogg"),
            SoundList("worp_e_04.ogg"),
            SoundList("worp_g_01.ogg"),
            SoundList("worp_g_02.ogg"),
            SoundList("worp_g_03.ogg"),
            SoundList("worp_g_04.ogg"),
        )

        oSoundList = arrayListOf(
            SoundList("break_a_01.ogg"),
            SoundList("break_a_02.ogg"),
            SoundList("break_a_03.ogg"),
            SoundList("break_a_04.ogg"),
            SoundList("break_a_05.ogg"),
            SoundList("break_a_06.ogg"),
            SoundList("break_a_07.ogg"),
            SoundList("break_a_08.ogg"),
            SoundList("break_a_09.ogg"),
            SoundList("break_a_10.ogg"),
            SoundList("breakfx_a_01.ogg"),
            SoundList("breakfx_a_02.ogg"),
            SoundList("breakfx_a_03.ogg"),
            SoundList("breakfx_a_04.ogg"),
            SoundList("breakfx_a_05.ogg"),
            SoundList("breakfx_a_06.ogg"),
            SoundList("breakfx_a_07.ogg"),
            SoundList("breakfx_a_08.ogg"),
            SoundList("breakfx_a_09.ogg"),
            SoundList("breakfx_a_10.ogg"),
            SoundList("goingbass_a_01.ogg"),
            SoundList("goingbass_a_02.ogg"),
            SoundList("goingbass_a_03.ogg"),
            SoundList("goingbass_a_04.ogg"),
            SoundList("goingbass_c_01.ogg"),
            SoundList("goingbass_c_02.ogg"),
            SoundList("goingbass_c_03.ogg"),
            SoundList("goingbass_c_04.ogg"),
            SoundList("goingbass_d_01.ogg"),
            SoundList("goingbass_d_02.ogg"),
            SoundList("goingbass_d_03.ogg"),
            SoundList("goingbass_d_04.ogg"),
            SoundList("goingbass_g_01.ogg"),
            SoundList("goingbass_g_02.ogg"),
            SoundList("goingbass_g_03.ogg"),
            SoundList("goingbass_g_04.ogg"),
            SoundList("goingbass_c1.ogg"),
            SoundList("goingbass_c2.ogg"),
            SoundList("goingbass_c3.ogg"),
            SoundList("goingbass_e1.ogg"),
            SoundList("goingbass_e2.ogg"),
            SoundList("goingbass_e3.ogg"),
            SoundList("goingbass_g_s1.ogg"),
            SoundList("goingbass_g_s2.ogg"),
            SoundList("goingbass_g_s3.ogg"),
            SoundList("peel_a_01.ogg"),
            SoundList("peel_a_02.ogg"),
            SoundList("peel_a_03.ogg"),
            SoundList("peel_a_04.ogg"),
            SoundList("peel_c_01.ogg"),
            SoundList("peel_c_02.ogg"),
            SoundList("peel_c_03.ogg"),
            SoundList("peel_c_04.ogg"),
            SoundList("peel_d_01.ogg"),
            SoundList("peel_d_02.ogg"),
            SoundList("peel_d_03.ogg"),
            SoundList("peel_d_04.ogg"),
            SoundList("peel_g_01.ogg"),
            SoundList("peel_g_02.ogg"),
            SoundList("peel_g_03.ogg"),
            SoundList("peel_g_04.ogg"),
            SoundList("spooch_a_01.ogg"),
            SoundList("spooch_a_02.ogg"),
            SoundList("spooch_a_03.ogg"),
            SoundList("spooch_a_04.ogg"),
            SoundList("spooch_a_05.ogg"),
            SoundList("spooch_a_06.ogg"),
            SoundList("spooch_a_07.ogg"),
            SoundList("spooch_a_08.ogg"),
            SoundList("spooch_b_01.ogg"),
            SoundList("spooch_b_02.ogg"),
            SoundList("spooch_b_03.ogg"),
            SoundList("spooch_b_04.ogg"),
            SoundList("spooch_b_05.ogg"),
            SoundList("spooch_b_06.ogg"),
            SoundList("spooch_b_07.ogg"),
            SoundList("spooch_b_08.ogg"),
            SoundList("synjun_a_01.ogg"),
            SoundList("synjun_a_02.ogg"),
            SoundList("synjun_a_03.ogg"),
            SoundList("synjun_a_04.ogg"),
            SoundList("synjun_c_01.ogg"),
            SoundList("synjun_c_02.ogg"),
            SoundList("synjun_c_03.ogg"),
            SoundList("synjun_c_04.ogg"),
            SoundList("synjun_d_01.ogg"),
            SoundList("synjun_d_02.ogg"),
            SoundList("synjun_d_03.ogg"),
            SoundList("synjun_d_04.ogg"),
            SoundList("synjun_g_01.ogg"),
            SoundList("synjun_g_02.ogg"),
            SoundList("synjun_g_03.ogg"),
            SoundList("synjun_g_04.ogg")
        )
        pSoundList = arrayListOf(
            SoundList("break_d_01.ogg"),
            SoundList("break_d_02.ogg"),
            SoundList("break_d_03.ogg"),
            SoundList("break_d_04.ogg"),
            SoundList("break_d_05.ogg"),
            SoundList("break_d_06.ogg"),
            SoundList("break_d_07.ogg"),
            SoundList("break_d_08.ogg"),
            SoundList("break_d_09.ogg"),
            SoundList("break_d_10.ogg"),
            SoundList("breakfx_d_01.ogg"),
            SoundList("breakfx_d_02.ogg"),
            SoundList("breakfx_d_03.ogg"),
            SoundList("breakfx_d_04.ogg"),
            SoundList("breakfx_d_05.ogg"),
            SoundList("breakfx_d_06.ogg"),
            SoundList("breakfx_d_07.ogg"),
            SoundList("breakfx_d_08.ogg"),
            SoundList("breakfx_d_09.ogg"),
            SoundList("breakfx_d_10.ogg"),
            SoundList("gnatter_a_01.ogg"),
            SoundList("gnatter_a_02.ogg"),
            SoundList("gnatter_a_03.ogg"),
            SoundList("gnatter_a_04.ogg"),
            SoundList("gnatter_c_01.ogg"),
            SoundList("gnatter_c_02.ogg"),
            SoundList("gnatter_c_03.ogg"),
            SoundList("gnatter_c_04.ogg"),
            SoundList("gnatter_e_01.ogg"),
            SoundList("gnatter_e_02.ogg"),
            SoundList("gnatter_e_03.ogg"),
            SoundList("gnatter_e_04.ogg"),
            SoundList("gnatter_g_01.ogg"),
            SoundList("gnatter_g_02.ogg"),
            SoundList("gnatter_g_03.ogg"),
            SoundList("gnatter_g_04.ogg"),
            SoundList("seerpad_a_01.ogg"),
            SoundList("seerpad_a_02.ogg"),
            SoundList("seerpad_a_03.ogg"),
            SoundList("seerpad_a_04.ogg"),
            SoundList("seerpad_c_01.ogg"),
            SoundList("seerpad_c_02.ogg"),
            SoundList("seerpad_c_03.ogg"),
            SoundList("seerpad_c_04.ogg"),
            SoundList("seerpad_e_01.ogg"),
            SoundList("seerpad_e_02.ogg"),
            SoundList("seerpad_e_03.ogg"),
            SoundList("seerpad_e_04.ogg"),
            SoundList("seerpad_g_01.ogg"),
            SoundList("seerpad_g_02.ogg"),
            SoundList("seerpad_g_03.ogg"),
            SoundList("seerpad_g_04.ogg"),
            SoundList("skbass_a_01.ogg"),
            SoundList("skbass_a_02.ogg"),
            SoundList("skbass_a_03.ogg"),
            SoundList("skbass_a_04.ogg"),
            SoundList("skbass_c_01.ogg"),
            SoundList("skbass_c_02.ogg"),
            SoundList("skbass_c_03.ogg"),
            SoundList("skbass_c_04.ogg"),
            SoundList("skbass_c1.ogg"),
            SoundList("skbass_c2.ogg"),
            SoundList("skbass_c3.ogg"),
            SoundList("skbass_e_01.ogg"),
            SoundList("skbass_e_02.ogg"),
            SoundList("skbass_e_03.ogg"),
            SoundList("skbass_e_04.ogg"),
            SoundList("skbass_e1.ogg"),
            SoundList("skbass_e2.ogg"),
            SoundList("skbass_e3.ogg"),
            SoundList("skbass_g_01.ogg"),
            SoundList("skbass_g_02.ogg"),
            SoundList("skbass_g_03.ogg"),
            SoundList("skbass_g_04.ogg"),
            SoundList("skbass_g_s1.ogg"),
            SoundList("skbass_g_s2.ogg"),
            SoundList("skbass_g_s3.ogg")
        )
        qSoundList = arrayListOf(
            SoundList("bludge_a_01.ogg"),
            SoundList("bludge_a_02.ogg"),
            SoundList("bludge_a_03.ogg"),
            SoundList("bludge_a_04.ogg"),
            SoundList("bludge_c_01.ogg"),
            SoundList("bludge_c_02.ogg"),
            SoundList("bludge_c_03.ogg"),
            SoundList("bludge_c_04.ogg"),
            SoundList("bludge_e_01.ogg"),
            SoundList("bludge_e_02.ogg"),
            SoundList("bludge_e_03.ogg"),
            SoundList("bludge_e_04.ogg"),
            SoundList("bludge_g_01.ogg"),
            SoundList("bludge_g_02.ogg"),
            SoundList("bludge_g_03.ogg"),
            SoundList("bludge_g_04.ogg"),
            SoundList("bludge_c1.ogg"),
            SoundList("bludge_c2.ogg"),
            SoundList("bludge_c3.ogg"),
            SoundList("bludge_e1.ogg"),
            SoundList("bludge_e2.ogg"),
            SoundList("bludge_e3.ogg"),
            SoundList("bludge_g_s1.ogg"),
            SoundList("bludge_g_s2.ogg"),
            SoundList("bludge_g_s3.ogg"),
            SoundList("break_b_01.ogg"),
            SoundList("break_b_02.ogg"),
            SoundList("break_b_03.ogg"),
            SoundList("break_b_04.ogg"),
            SoundList("break_b_05.ogg"),
            SoundList("break_b_06.ogg"),
            SoundList("break_b_07.ogg"),
            SoundList("break_b_08.ogg"),
            SoundList("break_b_09.ogg"),
            SoundList("break_b_10.ogg"),
            SoundList("halema_a_01.ogg"),
            SoundList("halema_a_02.ogg"),
            SoundList("halema_a_03.ogg"),
            SoundList("halema_c_01.ogg"),
            SoundList("halema_c_02.ogg"),
            SoundList("halema_c_03.ogg"),
            SoundList("halema_e_01.ogg"),
            SoundList("halema_e_02.ogg"),
            SoundList("halema_e_03.ogg"),
            SoundList("halema_g_01.ogg"),
            SoundList("halema_g_02.ogg"),
            SoundList("halema_g_03.ogg"),
            SoundList("thereman_a_01.ogg"),
            SoundList("thereman_a_02.ogg"),
            SoundList("thereman_a_03.ogg"),
            SoundList("thereman_a_04.ogg"),
            SoundList("thereman_c_01.ogg"),
            SoundList("thereman_c_02.ogg"),
            SoundList("thereman_c_03.ogg"),
            SoundList("thereman_c_04.ogg"),
            SoundList("thereman_e_01.ogg"),
            SoundList("thereman_e_02.ogg"),
            SoundList("thereman_e_03.ogg"),
            SoundList("thereman_e_04.ogg"),
            SoundList("thereman_g_01.ogg"),
            SoundList("thereman_g_02.ogg"),
            SoundList("thereman_g_03.ogg"),
            SoundList("thereman_g_04.ogg")
        )

        sSoundList = arrayListOf()
        tSoundList = arrayListOf()

        val listView = findViewById<ListView>(R.id.list_view)

        aCustomAdapter = CustomAdapter(this, aSoundList, this)
        bCustomAdapter = CustomAdapter(this, bSoundList, this)
        cCustomAdapter = CustomAdapter(this, cSoundList, this)
        dCustomAdapter = CustomAdapter(this, dSoundList, this)
        eCustomAdapter = CustomAdapter(this, eSoundList, this)
        nCustomAdapter = CustomAdapter(this, nSoundList, this)
        oCustomAdapter = CustomAdapter(this, oSoundList, this)
        pCustomAdapter = CustomAdapter(this, pSoundList, this)
        qCustomAdapter = CustomAdapter(this, qSoundList, this)
        sCustomAdapter = CustomAdapter(this, sSoundList, this)
        tCustomAdapter = CustomAdapter(this, tSoundList, this)

        listView.adapter = aCustomAdapter

        mp = MediaPlayer()

        supportActionBar?.title = actionTitle


            val audioUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI
            val cursor = contentResolver.query(audioUri, null, null, null, null)
            cursor!!.moveToFirst()
            val path: Array<String?> = arrayOfNulls(cursor.count)
            for (i in path.indices) {
                path[i] = cursor.getString(cursor.getColumnIndex("_data"))
                sSoundList.add(SoundList(path[i].toString()))
                cursor.moveToNext()
            }

            cursor.close()


        val meSpinner = findViewById<Spinner>(R.id.menu_spinner)

        val adapter3 = ArrayAdapter.createFromResource(this, R.array.spinnerItems, android.R.layout.simple_spinner_item)

        adapter3.setDropDownViewResource(R.layout.custom_spinner_dropdown)



        meSpinner.adapter = adapter3


        meSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?, position: Int, id: Long,
            ) {
                if (!meSpinner.isFocusable) {
                    meSpinner.isFocusable = true
                    return
                }

                when (position) {
                    0 -> {
                        buttonB = 2
                        soundListView.adapter = eCustomAdapter
                        eCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                    1 -> {
                        buttonB = 2
                        soundListView.adapter = aCustomAdapter
                        aCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                    2 -> {
                        buttonB = 2
                        soundListView.adapter = bCustomAdapter
                        bCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                    3 -> {
                        buttonB = 2
                        soundListView.adapter = cCustomAdapter
                        cCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                    4 -> {
                        buttonB = 2
                        soundListView.adapter = dCustomAdapter
                        dCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                    5 -> {
                        buttonB = 1
                        soundListView.adapter = sCustomAdapter
                        sCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                    6 -> {
                        selectEX()
                        buttonB = 1
                        soundListView.adapter = tCustomAdapter
                        tCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                }
            }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        meSpinner.isFocusable = false


        val audioAttributes = AudioAttributes.Builder()

                .setUsage(AudioAttributes.USAGE_GAME)

                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()

        soundPool = SoundPool.Builder()

                .setAudioAttributes(audioAttributes)

                .setMaxStreams(20)
                .build()

                sound1 = soundPool.load(assets.openFd("b_snare_01.ogg"), 1)

                sound2 = soundPool.load(assets.openFd("b_snare_02.ogg"), 1)

                sound3 = soundPool.load(assets.openFd("b_tamb_01.ogg"), 1)

                sound4 = soundPool.load(assets.openFd("b_tamb_02.ogg"), 1)

                sound5 = soundPool.load(assets.openFd("b_kick_01.ogg"), 1)

                sound6 = soundPool.load(assets.openFd("b_kick_02.ogg"), 1)

                sound7 = soundPool.load(assets.openFd("break_a_01.ogg"), 1)

                sound8 = soundPool.load(assets.openFd("break_a_02.ogg"), 1)

                sound9 = soundPool.load(assets.openFd("break_a_03.ogg"), 1)

                sound10 = soundPool.load(assets.openFd("break_a_04.ogg"), 1)

                sound11 = soundPool.load(assets.openFd("break_a_05.ogg"), 1)

                sound12 = soundPool.load(assets.openFd("break_a_06.ogg"), 1)

                sound13 = soundPool.load(assets.openFd("breakfx_a_01.ogg"), 1)

                sound14 = soundPool.load(assets.openFd("breakfx_a_02.ogg"), 1)

                sound15 = soundPool.load(assets.openFd("breakfx_a_03.ogg"), 1)

                lmp = LoopMediaPlayer.create(this, Uri.parse("android.resource://" + packageName + "/raw/" + R.raw.break_a_01))
        
        lmp.stop()


        binding.imageView.setOnTouchListener { _, event ->
            when {
                gridView.isVisible -> {
                    gridView.visibility = View.INVISIBLE
                }
                gridView2.isVisible -> {
                    gridView2.visibility = View.INVISIBLE
                }
                soundListView.isVisible -> {
                    soundListView.visibility = View.INVISIBLE
                }
                event.action == MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound1, soundPoolVolume, soundPoolVolume, 1, 0, soundPoolTempo)
                    effect(binding.imageView,"%.0f".format((mpDuration * 10) / (soundPoolTempo * 10)).toInt())
                }
            }
                false
        }

        binding.imageView2.setOnTouchListener { _, event ->
            when {
                gridView.isVisible -> {
                    gridView.visibility = View.INVISIBLE
                }
                gridView2.isVisible -> {
                    gridView2.visibility = View.INVISIBLE
                }
                soundListView.isVisible -> {
                    soundListView.visibility = View.INVISIBLE
                }
                event.action == MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound2, soundPoolVolume2, soundPoolVolume2, 1, 0, soundPoolTempo2)
                    effect(binding.imageView2,"%.0f".format((mpDuration2 * 10) / (soundPoolTempo2 * 10)).toInt())
                }
            }
                false
        }

        binding.imageView3.setOnTouchListener { _, event ->
            when {
                gridView.isVisible -> {
                    gridView.visibility = View.INVISIBLE
                }
                gridView2.isVisible -> {
                    gridView2.visibility = View.INVISIBLE
                }
                soundListView.isVisible -> {
                    soundListView.visibility = View.INVISIBLE
                }
                event.action == MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound3, soundPoolVolume3, soundPoolVolume3, 1, 0, soundPoolTempo3)
                    effect(binding.imageView3,"%.0f".format((mpDuration3 * 10) / (soundPoolTempo3 * 10)).toInt())
                }
            }
                false
        }

        binding.imageView4.setOnTouchListener { _, event ->
            when {
                gridView.isVisible -> {
                    gridView.visibility = View.INVISIBLE
                }
                gridView2.isVisible -> {
                    gridView2.visibility = View.INVISIBLE
                }
                soundListView.isVisible -> {
                    soundListView.visibility = View.INVISIBLE
                }
                event.action == MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound4, soundPoolVolume4, soundPoolVolume4, 1, 0, soundPoolTempo4)
                    effect(binding.imageView4,"%.0f".format((mpDuration4 * 10) / (soundPoolTempo4 * 10)).toInt())
                }
            }
                false
        }

        binding.imageView5.setOnTouchListener { _, event ->
            when {
                gridView.isVisible -> {
                    gridView.visibility = View.INVISIBLE
                }
                gridView2.isVisible -> {
                    gridView2.visibility = View.INVISIBLE
                }
                soundListView.isVisible -> {
                    soundListView.visibility = View.INVISIBLE
                }
                event.action == MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound5, soundPoolVolume5, soundPoolVolume5, 1, 0, soundPoolTempo5)
                    effect(binding.imageView5,"%.0f".format((mpDuration5 * 10) / (soundPoolTempo5 * 10)).toInt())
                }
            }
                false
        }

        binding.imageView6.setOnTouchListener { _, event ->
            when {
                gridView.isVisible -> {
                    gridView.visibility = View.INVISIBLE
                }
                gridView2.isVisible -> {
                    gridView2.visibility = View.INVISIBLE
                }
                soundListView.isVisible -> {
                    soundListView.visibility = View.INVISIBLE
                }
                event.action == MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound6, soundPoolVolume6, soundPoolVolume6, 1, 0, soundPoolTempo6)
                    effect(binding.imageView6,"%.0f".format((mpDuration6 * 10) / (soundPoolTempo6 * 10)).toInt())
                }
            }
                false
        }

        binding.imageView7.setOnTouchListener { _, event ->
            when {
                gridView.isVisible -> {
                    gridView.visibility = View.INVISIBLE
                }
                gridView2.isVisible -> {
                    gridView2.visibility = View.INVISIBLE
                }
                soundListView.isVisible -> {
                    soundListView.visibility = View.INVISIBLE
                }
                event.action == MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound7, soundPoolVolume7, soundPoolVolume7, 1, 0, soundPoolTempo7)
                    effect(binding.imageView7,"%.0f".format((mpDuration7 * 10) / (soundPoolTempo7 * 10)).toInt())
                }
            }
                false
        }

        binding.imageView8.setOnTouchListener { _, event ->
            when {
                gridView.isVisible -> {
                    gridView.visibility = View.INVISIBLE
                }
                gridView2.isVisible -> {
                    gridView2.visibility = View.INVISIBLE
                }
                soundListView.isVisible -> {
                    soundListView.visibility = View.INVISIBLE
                }
                event.action == MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound8, soundPoolVolume8, soundPoolVolume8, 1, 0, soundPoolTempo8)
                    effect(binding.imageView8,"%.0f".format((mpDuration8 * 10) / (soundPoolTempo8 * 10)).toInt())
                }
            }
                false
        }

        binding.imageView9.setOnTouchListener { _, event ->
            when {
                gridView.isVisible -> {
                    gridView.visibility = View.INVISIBLE
                }
                gridView2.isVisible -> {
                    gridView2.visibility = View.INVISIBLE
                }
                soundListView.isVisible -> {
                    soundListView.visibility = View.INVISIBLE
                }
                event.action == MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound9, soundPoolVolume9, soundPoolVolume9, 1, 0, soundPoolTempo9)
                    effect(binding.imageView9,"%.0f".format((mpDuration9 * 10) / (soundPoolTempo9 * 10)).toInt())
                }
            }
                false

        }

        binding.imageView10.setOnTouchListener { _, event ->
            when {
                gridView.isVisible -> {
                    gridView.visibility = View.INVISIBLE
                }
                gridView2.isVisible -> {
                    gridView2.visibility = View.INVISIBLE
                }
                soundListView.isVisible -> {
                    soundListView.visibility = View.INVISIBLE
                }
                event.action == MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound10, soundPoolVolume10, soundPoolVolume10, 1, 0, soundPoolTempo10)
                    effect(binding.imageView10,"%.0f".format((mpDuration10 * 10) / (soundPoolTempo10 * 10)).toInt())
                }
            }
                false
        }

        binding.imageView11.setOnTouchListener { _, event ->
            when {
                gridView.isVisible -> {
                    gridView.visibility = View.INVISIBLE
                }
                gridView2.isVisible -> {
                    gridView2.visibility = View.INVISIBLE
                }
                soundListView.isVisible -> {
                    soundListView.visibility = View.INVISIBLE
                }
                event.action == MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound11, soundPoolVolume11, soundPoolVolume11, 1, 0, soundPoolTempo11)
                    effect(binding.imageView11,"%.0f".format((mpDuration11 * 10) / (soundPoolTempo11 * 10)).toInt())
                }
            }
                false
        }

        binding.imageView12.setOnTouchListener { _, event ->
            when {
                gridView.isVisible -> {
                    gridView.visibility = View.INVISIBLE
                }
                gridView2.isVisible -> {
                    gridView2.visibility = View.INVISIBLE
                }
                soundListView.isVisible -> {
                    soundListView.visibility = View.INVISIBLE
                }
                event.action == MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound12, soundPoolVolume12, soundPoolVolume12, 1, 0, soundPoolTempo12)
                    effect(binding.imageView12,"%.0f".format((mpDuration12 * 10) / (soundPoolTempo12 * 10)).toInt())
                }
            }
                false
        }

        binding.imageView13.setOnTouchListener { _, event ->
            when {
                gridView.isVisible -> {
                    gridView.visibility = View.INVISIBLE
                }
                gridView2.isVisible -> {
                    gridView2.visibility = View.INVISIBLE
                }
                soundListView.isVisible -> {
                    soundListView.visibility = View.INVISIBLE
                }
                event.action == MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound13, soundPoolVolume13, soundPoolVolume13, 1, 0, soundPoolTempo13)
                    effect(binding.imageView13,"%.0f".format((mpDuration13 * 10) / (soundPoolTempo13 * 10)).toInt())
                }
            }
                false
        }

        binding.imageView14.setOnTouchListener { _, event ->
            when {
                gridView.isVisible -> {
                    gridView.visibility = View.INVISIBLE
                }
                gridView2.isVisible -> {
                    gridView2.visibility = View.INVISIBLE
                }
                soundListView.isVisible -> {
                    soundListView.visibility = View.INVISIBLE
                }
                event.action == MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound14, soundPoolVolume14, soundPoolVolume14, 1, 0, soundPoolTempo14)
                    effect(binding.imageView14,"%.0f".format((mpDuration14 * 10) / (soundPoolTempo14 * 10)).toInt())
                }
            }
                false
        }

        binding.imageView15.setOnTouchListener { _, event ->
            when {
                gridView.isVisible -> {
                    gridView.visibility = View.INVISIBLE
                }
                gridView2.isVisible -> {
                    gridView2.visibility = View.INVISIBLE
                }
                soundListView.isVisible -> {
                    soundListView.visibility = View.INVISIBLE
                }
                event.action == MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound15, soundPoolVolume15, soundPoolVolume15, 1, 0, soundPoolTempo15)
                    effect(binding.imageView15,"%.0f".format((mpDuration15 * 10) / (soundPoolTempo15 * 10)).toInt())
                }
            }
                false
        }


        binding.imageView.setOnClickListener {
            if (paste == 1) {
                buttonA = 1
                meSpinner.avoidDropdownFocus()
                meSpinner.performClick()
            }
        }
        binding.imageView2.setOnClickListener {
            if (paste == 1) {
                buttonA = 2
                meSpinner.avoidDropdownFocus()
                meSpinner.performClick()
            }
        }
        binding.imageView3.setOnClickListener {
            if (paste == 1) {
                buttonA = 3
                meSpinner.avoidDropdownFocus()
                meSpinner.performClick()
            }
        }
        binding.imageView4.setOnClickListener {
            if (paste == 1) {
                buttonA = 4
                meSpinner.avoidDropdownFocus()
                meSpinner.performClick()
            }
        }
        binding.imageView5.setOnClickListener {
            if (paste == 1) {
                buttonA = 5
                meSpinner.avoidDropdownFocus()
                meSpinner.performClick()
            }
        }
        binding.imageView6.setOnClickListener {
            if (paste == 1) {
                buttonA = 6
                meSpinner.avoidDropdownFocus()
                meSpinner.performClick()
            }
        }
        binding.imageView7.setOnClickListener {
            if (paste == 1) {
                buttonA = 7
                meSpinner.avoidDropdownFocus()
                meSpinner.performClick()
            }
        }
        binding.imageView8.setOnClickListener {
            if (paste == 1) {
                buttonA = 8
                meSpinner.avoidDropdownFocus()
                meSpinner.performClick()
            }
        }
        binding.imageView9.setOnClickListener {
            if (paste == 1) {
                buttonA = 9
                meSpinner.avoidDropdownFocus()
                meSpinner.performClick()
            }
        }
        binding.imageView10.setOnClickListener {
            if (paste == 1) {
                buttonA = 10
                meSpinner.avoidDropdownFocus()
                meSpinner.performClick()
            }
        }
        binding.imageView11.setOnClickListener {
            if (paste == 1) {
                buttonA = 11
                meSpinner.avoidDropdownFocus()
                meSpinner.performClick()
            }
        }
        binding.imageView12.setOnClickListener {
            if (paste == 1) {
                buttonA = 12
                meSpinner.avoidDropdownFocus()
                meSpinner.performClick()
            }
        }
        binding.imageView13.setOnClickListener {
            if (paste == 1) {
                buttonA = 13
                meSpinner.avoidDropdownFocus()
                meSpinner.performClick()
            }
        }
        binding.imageView14.setOnClickListener {
            if (paste == 1) {
                buttonA = 14
                meSpinner.avoidDropdownFocus()
                meSpinner.performClick()
            }
        }
        binding.imageView15.setOnClickListener {
            if (paste == 1) {
                buttonA = 15
                meSpinner.avoidDropdownFocus()
                meSpinner.performClick()
            }
        }

        findViewById<ImageButton>(R.id.volume_minus0).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lmp.volumeMinus()
                    if (count > 0.1f) {
                        count -= 0.1f
                        findViewById<Button>(R.id.loop).text = ""
                        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                            count = "%.1f".format(count).toFloat()
                            findViewById<TextView>(R.id.padText0).text = count.toString().replace("f", "") + " " + actionTitle + " " + bpm.toString().replace("f", "").uppercase()
                        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            count = "%.1f".format(count).toFloat()
                            findViewById<TextView>(R.id.padText0).text = count.toString().replace("f", "") + "\n\n" + "loop" + "\n\n" + bpm.toString().replace("f", "").uppercase()
                        }
                    }
                }
            }
            false
        }
        findViewById<ImageButton>(R.id.volume_plus0).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lmp.volumePlus()
                    if (count < 1.0f) {
                        count += 0.1f
                        findViewById<Button>(R.id.loop).text = ""
                        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                            count = "%.1f".format(count).toFloat()
                            findViewById<TextView>(R.id.padText0).text = count.toString().replace("f", "") + " " + actionTitle + " " + bpm.toString().replace("f", "").uppercase()
                        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            count = "%.1f".format(count).toFloat()
                            findViewById<TextView>(R.id.padText0).text = count.toString().replace("f", "") + "\n\n" + "loop" + "\n\n" + bpm.toString().replace("f", "").uppercase()
                        }
                    }
                }
            }
            false
        }
        findViewById<ImageButton>(R.id.tempo_minus0).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lmp.speedDown()
                    if (bpm > 0.1f) {
                        bpm -= 0.1f
                        findViewById<Button>(R.id.loop).text = ""
                        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                            bpm = "%.1f".format(bpm).toFloat()
                            findViewById<TextView>(R.id.padText0).text = count.toString().replace("f", "") + " " + actionTitle + " " + bpm.toString().replace("f", "").uppercase()
                        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            bpm = "%.1f".format(bpm).toFloat()
                            findViewById<TextView>(R.id.padText0).text = count.toString().replace("f", "") + "\n\n" + "loop" + "\n\n" + bpm.toString().replace("f", "").uppercase()
                        }
                        menuSwitch = false
                        invalidateOptionsMenu()
                        switch1 = 1
                    }
                }
            }
            false
        }
        findViewById<ImageButton>(R.id.tempo_plus0).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lmp.speedUp()
                    if (bpm < 6.0f) {
                        bpm += 0.1f
                        findViewById<Button>(R.id.loop).text = ""
                        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                            bpm = "%.1f".format(bpm).toFloat()
                            findViewById<TextView>(R.id.padText0).text = count.toString().replace("f", "") + " " + actionTitle + " " + bpm.toString().replace("f", "").uppercase()
                        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            bpm = "%.1f".format(bpm).toFloat()
                            findViewById<TextView>(R.id.padText0).text = count.toString().replace("f", "") + "\n\n" + "loop" + "\n\n" + bpm.toString().replace("f", "").uppercase()
                        }
                        menuSwitch = false
                        invalidateOptionsMenu()
                        switch1 = 1
                    }
                }
            }
            false
        }

        findViewById<Button>(R.id.loop).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (switch1 == 1) {
                        lmp.stop()
                        soundPool.autoPause()
                        menuSwitch = true
                        invalidateOptionsMenu()
                        switch1 = 2
                    } else {
                        lmp.start()
                        menuSwitch = false
                        invalidateOptionsMenu()
                        switch1 = 1
                    }
                }
            }
            false
        }
        findViewById<View>(R.id.include_view).findViewById<ImageButton>(
            R.id.pad).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound1, soundPoolVolume, soundPoolVolume, 1, 0, soundPoolTempo)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view2).findViewById<ImageButton>(
            R.id.pad).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound2, soundPoolVolume2, soundPoolVolume2, 1, 0, soundPoolTempo2)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view3).findViewById<ImageButton>(
            R.id.pad).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound3, soundPoolVolume3, soundPoolVolume3, 1, 0, soundPoolTempo3)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view4).findViewById<ImageButton>(
            R.id.pad).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound4, soundPoolVolume4, soundPoolVolume4, 1, 0, soundPoolTempo4)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view5).findViewById<ImageButton>(
            R.id.pad).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound5, soundPoolVolume5, soundPoolVolume5, 1, 0, soundPoolTempo5)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view6).findViewById<ImageButton>(
            R.id.pad).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound6, soundPoolVolume6, soundPoolVolume6, 1, 0, soundPoolTempo6)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view7).findViewById<ImageButton>(
            R.id.pad).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound7, soundPoolVolume7, soundPoolVolume7, 1, 0, soundPoolTempo7)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view8).findViewById<ImageButton>(
            R.id.pad).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound8, soundPoolVolume8, soundPoolVolume8, 1, 0, soundPoolTempo8)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view9).findViewById<ImageButton>(
            R.id.pad).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound9, soundPoolVolume9, soundPoolVolume9, 1, 0, soundPoolTempo9)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view10).findViewById<ImageButton>(
            R.id.pad).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound10, soundPoolVolume10, soundPoolVolume10, 1, 0, soundPoolTempo10)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view11).findViewById<ImageButton>(
            R.id.pad).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound11, soundPoolVolume11, soundPoolVolume11, 1, 0, soundPoolTempo11)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view12).findViewById<ImageButton>(
            R.id.pad).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound12, soundPoolVolume12, soundPoolVolume12, 1, 0, soundPoolTempo12)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view13).findViewById<ImageButton>(
            R.id.pad).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound13, soundPoolVolume13, soundPoolVolume13, 1, 0, soundPoolTempo13)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view14).findViewById<ImageButton>(
            R.id.pad).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound14, soundPoolVolume14, soundPoolVolume14, 1, 0, soundPoolTempo14)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view15).findViewById<ImageButton>(
            R.id.pad).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    soundPool.play(sound15, soundPoolVolume15, soundPoolVolume15, 1, 0, soundPoolTempo15)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view).findViewById<ImageButton>(
            R.id.volume_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume > 0.1f) {
                        soundPoolVolume -= 0.1f
                        soundPoolVolume = "%.1f".format(soundPoolVolume).toFloat()
                        findViewById<View>(R.id.include_view).findViewById<TextView>(
                            R.id.padText).text =
                            ""
                        findViewById<View>(R.id.include_view).findViewById<TextView>(
                            R.id.padText).text =
                            soundPoolVolume.toString()
                                .replace("f", "") + "            " + soundPoolTempo.toString()
                                .replace("f", "") + "\n" + padText1.uppercase()
                    }
                    soundPool.play(sound1, soundPoolVolume, soundPoolVolume, 1, 0, soundPoolTempo)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view).findViewById<ImageButton>(
            R.id.volume_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume < 1.0f) {
                        soundPoolVolume += 0.1f
                        soundPoolVolume = "%.1f".format(soundPoolVolume).toFloat()
                        findViewById<View>(R.id.include_view).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume.toString().replace("f", "") + "            " + soundPoolTempo.toString().replace("f", "") + "\n" + padText1.uppercase()
                    }
                    soundPool.play(sound1, soundPoolVolume, soundPoolVolume, 1, 0, soundPoolTempo)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view).findViewById<ImageButton>(
            R.id.tempo_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo > 0.2f) {
                        soundPoolTempo -= 0.1f
                        soundPoolTempo = "%.1f".format(soundPoolTempo).toFloat()
                        findViewById<View>(R.id.include_view).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume.toString().replace("f", "") + "            " + soundPoolTempo.toString().replace("f", "") + "\n" + padText1.uppercase()
                    } else if (soundPoolTempo == 0.2f) {
                        soundPoolTempo = 0.125f
                        findViewById<View>(R.id.include_view).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume.toString().replace("f", "") + "            " + soundPoolTempo.toString().replace("f", "") + "\n" + padText1.uppercase()
                    }
                    soundPool.play(sound1, soundPoolVolume, soundPoolVolume, 1, 0, soundPoolTempo)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view).findViewById<ImageButton>(
            R.id.tempo_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo == 0.125f) {
                        soundPoolTempo = 0.2f
                        findViewById<View>(R.id.include_view).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume.toString().replace("f", "") + "            " + soundPoolTempo.toString().replace("f", "") + "\n" + padText1.uppercase()
                    } else if (soundPoolTempo < 8.0f) {
                        soundPoolTempo += 0.1f
                        soundPoolTempo = "%.1f".format(soundPoolTempo).toFloat()
                        findViewById<View>(R.id.include_view).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume.toString().replace("f", "") + "            " + soundPoolTempo.toString().replace("f", "") + "\n" + padText1.uppercase()
                    }
                    soundPool.play(sound1, soundPoolVolume, soundPoolVolume, 1, 0, soundPoolTempo)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view2).findViewById<ImageButton>(
            R.id.volume_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume2 > 0.1f) {
                        soundPoolVolume2 -= 0.1f
                        soundPoolVolume2 = "%.1f".format(soundPoolVolume2).toFloat()
                        findViewById<View>(R.id.include_view2).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view2).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume2.toString().replace("f", "") + "            " + soundPoolTempo2.toString().replace("f", "") + "\n" + padText2.uppercase()
                    }
                    soundPool.play(sound2, soundPoolVolume2, soundPoolVolume2, 1, 0, soundPoolTempo2)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view2).findViewById<ImageButton>(
            R.id.volume_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume2 < 1.0f) {
                        soundPoolVolume2 += 0.1f
                        soundPoolVolume2 = "%.1f".format(soundPoolVolume2).toFloat()
                        findViewById<View>(R.id.include_view2).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view2).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume2.toString().replace("f", "") + "            " + soundPoolTempo2.toString().replace("f", "") + "\n" + padText2.uppercase()
                    }
                    soundPool.play(sound2, soundPoolVolume2, soundPoolVolume2, 1, 0, soundPoolTempo2)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view2).findViewById<ImageButton>(
            R.id.tempo_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo2 > 0.2f) {
                        soundPoolTempo2 -= 0.1f
                        soundPoolTempo2 = "%.1f".format(soundPoolTempo2).toFloat()
                        findViewById<View>(R.id.include_view2).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view2).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume2.toString().replace("f", "") + "            " + soundPoolTempo2.toString().replace("f", "") + "\n" + padText2.uppercase()
                    } else if (soundPoolTempo2 == 0.2f) {
                        soundPoolTempo2 = 0.125f
                        findViewById<View>(R.id.include_view2).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view2).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume2.toString().replace("f", "") + "            " + soundPoolTempo2.toString().replace("f", "") + "\n" + padText2.uppercase()
                    }
                    soundPool.play(sound2, soundPoolVolume2, soundPoolVolume2, 1, 0, soundPoolTempo2)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view2).findViewById<ImageButton>(
            R.id.tempo_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo2 == 0.125f) {
                        soundPoolTempo2 = 0.2f
                        findViewById<View>(R.id.include_view2).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view2).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume2.toString().replace("f", "") + "            " + soundPoolTempo2.toString().replace("f", "") + "\n" + padText2.uppercase()
                    } else if (soundPoolTempo2 < 8.0f) {
                        soundPoolTempo2 += 0.1f
                        soundPoolTempo2 = "%.1f".format(soundPoolTempo2).toFloat()
                        findViewById<View>(R.id.include_view2).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view2).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume2.toString().replace("f", "") + "            " + soundPoolTempo2.toString().replace("f", "") + "\n" + padText2.uppercase()
                    }
                    soundPool.play(sound2, soundPoolVolume2, soundPoolVolume2, 1, 0, soundPoolTempo2)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view3).findViewById<ImageButton>(
            R.id.volume_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume3 > 0.1f) {
                        soundPoolVolume3 -= 0.1f
                        soundPoolVolume3 = "%.1f".format(soundPoolVolume3).toFloat()
                        findViewById<View>(R.id.include_view3).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view3).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume3.toString().replace("f", "") + "            " + soundPoolTempo3.toString().replace("f", "") + "\n" + padText3.uppercase()
                    }
                    soundPool.play(sound3, soundPoolVolume3, soundPoolVolume3, 1, 0, soundPoolTempo3)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view3).findViewById<ImageButton>(
            R.id.volume_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume3 < 1.0f) {
                        soundPoolVolume3 += 0.1f
                        soundPoolVolume3 = "%.1f".format(soundPoolVolume3).toFloat()
                        findViewById<View>(R.id.include_view3).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view3).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume3.toString().replace("f", "") + "            " + soundPoolTempo3.toString().replace("f", "") + "\n" + padText3.uppercase()
                    }
                    soundPool.play(sound3, soundPoolVolume3, soundPoolVolume3, 1, 0, soundPoolTempo3)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view3).findViewById<ImageButton>(
            R.id.tempo_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo3 > 0.2f) {
                        soundPoolTempo3 -= 0.1f
                        soundPoolTempo3 = "%.1f".format(soundPoolTempo3).toFloat()
                        findViewById<View>(R.id.include_view3).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view3).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume3.toString().replace("f", "") + "            " + soundPoolTempo3.toString().replace("f", "") + "\n" + padText3.uppercase()
                    } else if (soundPoolTempo3 == 0.2f) {
                        soundPoolTempo3 = 0.125f
                        findViewById<View>(R.id.include_view3).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view3).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume3.toString().replace("f", "") + "            " + soundPoolTempo3.toString().replace("f", "") + "\n" + padText3.uppercase()
                    }
                    soundPool.play(sound3, soundPoolVolume3, soundPoolVolume3, 1, 0, soundPoolTempo3)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view3).findViewById<ImageButton>(
            R.id.tempo_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo3 == 0.125f) {
                        soundPoolTempo3 = 0.2f
                        findViewById<View>(R.id.include_view3).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view3).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume3.toString().replace("f", "") + "            " + soundPoolTempo3.toString().replace("f", "") + "\n" + padText3.uppercase()
                    } else if (soundPoolTempo3 < 8.0f) {
                        soundPoolTempo3 += 0.1f
                        soundPoolTempo3 = "%.1f".format(soundPoolTempo3).toFloat()
                        findViewById<View>(R.id.include_view3).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view3).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume3.toString().replace("f", "") + "            " + soundPoolTempo3.toString().replace("f", "") + "\n" + padText3.uppercase()
                    }
                    soundPool.play(sound3, soundPoolVolume3, soundPoolVolume3, 1, 0, soundPoolTempo3)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view4).findViewById<ImageButton>(
            R.id.volume_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume4 > 0.1f) {
                        soundPoolVolume4 -= 0.1f
                        soundPoolVolume4 = "%.1f".format(soundPoolVolume4).toFloat()
                        findViewById<View>(R.id.include_view4).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view4).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume4.toString().replace("f", "") + "            " + soundPoolTempo4.toString().replace("f", "") + "\n" + padText4.uppercase()
                    }
                    soundPool.play(sound4, soundPoolVolume4, soundPoolVolume4, 1, 0, soundPoolTempo4)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view4).findViewById<ImageButton>(
            R.id.volume_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume4 < 1.0f) {
                        soundPoolVolume4 += 0.1f
                        soundPoolVolume4 = "%.1f".format(soundPoolVolume4).toFloat()
                        findViewById<View>(R.id.include_view4).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view4).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume4.toString().replace("f", "") + "            " + soundPoolTempo4.toString().replace("f", "") + "\n" + padText4.uppercase()
                    }
                    soundPool.play(sound4, soundPoolVolume4, soundPoolVolume4, 1, 0, soundPoolTempo4)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view4).findViewById<ImageButton>(
            R.id.tempo_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo4 > 0.2f) {
                        soundPoolTempo4 -= 0.1f
                        soundPoolTempo4 = "%.1f".format(soundPoolTempo4).toFloat()
                        findViewById<View>(R.id.include_view4).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view4).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume4.toString().replace("f", "") + "            " + soundPoolTempo4.toString().replace("f", "") + "\n" + padText4.uppercase()
                    } else if (soundPoolTempo4 == 0.2f) {
                        soundPoolTempo4 = 0.125f
                        findViewById<View>(R.id.include_view4).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view4).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume4.toString().replace("f", "") + "            " + soundPoolTempo4.toString().replace("f", "") + "\n" + padText4.uppercase()
                    }
                    soundPool.play(sound4, soundPoolVolume4, soundPoolVolume4, 1, 0, soundPoolTempo4)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view4).findViewById<ImageButton>(
            R.id.tempo_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo4 == 0.125f) {
                        soundPoolTempo4 = 0.2f
                        findViewById<View>(R.id.include_view4).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view4).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume4.toString().replace("f", "") + "            " + soundPoolTempo4.toString().replace("f", "") + "\n" + padText4.uppercase()
                    } else if (soundPoolTempo4 < 8.0f) {
                        soundPoolTempo4 += 0.1f
                        soundPoolTempo4 = "%.1f".format(soundPoolTempo4).toFloat()
                        findViewById<View>(R.id.include_view4).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view4).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume4.toString().replace("f", "") + "            " + soundPoolTempo4.toString().replace("f", "") + "\n" + padText4.uppercase()
                    }
                    soundPool.play(sound4, soundPoolVolume4, soundPoolVolume4, 1, 0, soundPoolTempo4)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view5).findViewById<ImageButton>(
            R.id.volume_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume5 > 0.1f) {
                        soundPoolVolume5 -= 0.1f
                        soundPoolVolume5 = "%.1f".format(soundPoolVolume5).toFloat()
                        findViewById<View>(R.id.include_view5).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view5).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume5.toString().replace("f", "") + "            " + soundPoolTempo5.toString().replace("f", "") + "\n" + padText5.uppercase()
                    }
                    soundPool.play(sound5, soundPoolVolume5, soundPoolVolume5, 1, 0, soundPoolTempo5)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view5).findViewById<ImageButton>(
            R.id.volume_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume5 < 1.0f) {
                        soundPoolVolume5 += 0.1f
                        soundPoolVolume5 = "%.1f".format(soundPoolVolume5).toFloat()
                        findViewById<View>(R.id.include_view5).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view5).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume5.toString().replace("f", "") + "            " + soundPoolTempo5.toString().replace("f", "") + "\n" + padText5.uppercase()
                    }
                    soundPool.play(sound5, soundPoolVolume5, soundPoolVolume5, 1, 0, soundPoolTempo5)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view5).findViewById<ImageButton>(
            R.id.tempo_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo5 > 0.2f) {
                        soundPoolTempo5 -= 0.1f
                        soundPoolTempo5 = "%.1f".format(soundPoolTempo5).toFloat()
                        findViewById<View>(R.id.include_view5).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view5).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume5.toString().replace("f", "") + "            " + soundPoolTempo5.toString().replace("f", "") + "\n" + padText5.uppercase()
                    } else if (soundPoolTempo5 == 0.2f) {
                        soundPoolTempo5 = 0.125f
                        findViewById<View>(R.id.include_view5).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view5).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume5.toString().replace("f", "") + "            " + soundPoolTempo5.toString().replace("f", "") + "\n" + padText5.uppercase()
                    }
                    soundPool.play(sound5, soundPoolVolume5, soundPoolVolume5, 1, 0, soundPoolTempo5)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view5).findViewById<ImageButton>(
            R.id.tempo_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo5 == 0.125f) {
                        soundPoolTempo5 = 0.2f
                        findViewById<View>(R.id.include_view5).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view5).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume5.toString().replace("f", "") + "            " + soundPoolTempo5.toString().replace("f", "") + "\n" + padText5.uppercase()
                    } else if (soundPoolTempo5 < 8.0f) {
                        soundPoolTempo5 += 0.1f
                        soundPoolTempo5 = "%.1f".format(soundPoolTempo5).toFloat()
                        findViewById<View>(R.id.include_view5).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view5).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume5.toString().replace("f", "") + "            " + soundPoolTempo5.toString().replace("f", "") + "\n" + padText5.uppercase()
                    }
                    soundPool.play(sound5, soundPoolVolume5, soundPoolVolume5, 1, 0, soundPoolTempo5)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view6).findViewById<ImageButton>(
            R.id.volume_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume6 > 0.1f) {
                        soundPoolVolume6 -= 0.1f
                        soundPoolVolume6 = "%.1f".format(soundPoolVolume6).toFloat()
                        findViewById<View>(R.id.include_view6).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view6).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume6.toString().replace("f", "") + "            " + soundPoolTempo6.toString().replace("f", "") + "\n" + padText6.uppercase()
                    }
                    soundPool.play(sound6, soundPoolVolume6, soundPoolVolume6, 1, 0, soundPoolTempo6)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view6).findViewById<ImageButton>(
            R.id.volume_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume6 < 1.0f) {
                        soundPoolVolume6 += 0.1f
                        soundPoolVolume6 = "%.1f".format(soundPoolVolume6).toFloat()
                        findViewById<View>(R.id.include_view6).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view6).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume6.toString().replace("f", "") + "            " + soundPoolTempo6.toString().replace("f", "") + "\n" + padText6.uppercase()
                    }
                    soundPool.play(sound6, soundPoolVolume6, soundPoolVolume6, 1, 0, soundPoolTempo6)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view6).findViewById<ImageButton>(
            R.id.tempo_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo6 > 0.2f) {
                        soundPoolTempo6 -= 0.1f
                        soundPoolTempo6 = "%.1f".format(soundPoolTempo6).toFloat()
                        findViewById<View>(R.id.include_view6).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view6).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume6.toString().replace("f", "") + "            " + soundPoolTempo6.toString().replace("f", "") + "\n" + padText6.uppercase()
                    } else if (soundPoolTempo6 == 0.2f) {
                        soundPoolTempo6 = 0.125f
                        findViewById<View>(R.id.include_view6).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view6).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume6.toString().replace("f", "") + "            " + soundPoolTempo6.toString().replace("f", "") + "\n" + padText6.uppercase()
                    }
                    soundPool.play(sound6, soundPoolVolume6, soundPoolVolume6, 1, 0, soundPoolTempo6)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view6).findViewById<ImageButton>(
            R.id.tempo_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo6 == 0.125f) {
                        soundPoolTempo6 = 0.2f
                        findViewById<View>(R.id.include_view6).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view6).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume6.toString().replace("f", "") + "            " + soundPoolTempo6.toString().replace("f", "") + "\n" + padText6.uppercase()
                    } else if (soundPoolTempo6 < 8.0f) {
                        soundPoolTempo6 += 0.1f
                        soundPoolTempo6 = "%.1f".format(soundPoolTempo6).toFloat()
                        findViewById<View>(R.id.include_view6).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view6).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume6.toString().replace("f", "") + "            " + soundPoolTempo6.toString().replace("f", "") + "\n" + padText6.uppercase()
                    }
                    soundPool.play(sound6, soundPoolVolume6, soundPoolVolume6, 1, 0, soundPoolTempo6)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view7).findViewById<ImageButton>(
            R.id.volume_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume7 > 0.1f) {
                        soundPoolVolume7 -= 0.1f
                        soundPoolVolume7 = "%.1f".format(soundPoolVolume7).toFloat()
                        findViewById<View>(R.id.include_view7).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view7).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume7.toString().replace("f", "") + "            " + soundPoolTempo7.toString().replace("f", "") + "\n" + padText7.uppercase()
                    }
                    soundPool.play(sound7, soundPoolVolume7, soundPoolVolume7, 1, 0, soundPoolTempo7)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view7).findViewById<ImageButton>(
            R.id.volume_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume7 < 1.0f) {
                        soundPoolVolume7 += 0.1f
                        soundPoolVolume7 = "%.1f".format(soundPoolVolume7).toFloat()
                        findViewById<View>(R.id.include_view7).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view7).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume7.toString().replace("f", "") + "            " + soundPoolTempo7.toString().replace("f", "") + "\n" + padText7.uppercase()
                    }
                    soundPool.play(sound7, soundPoolVolume7, soundPoolVolume7, 1, 0, soundPoolTempo7)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view7).findViewById<ImageButton>(
            R.id.tempo_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo7 > 0.2f) {
                        soundPoolTempo7 -= 0.1f
                        soundPoolTempo7 = "%.1f".format(soundPoolTempo7).toFloat()
                        findViewById<View>(R.id.include_view7).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view7).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume7.toString().replace("f", "") + "            " + soundPoolTempo7.toString().replace("f", "") + "\n" + padText7.uppercase()
                    } else if (soundPoolTempo7 == 0.2f) {
                        soundPoolTempo7 = 0.125f
                        findViewById<View>(R.id.include_view7).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view7).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume7.toString().replace("f", "") + "            " + soundPoolTempo7.toString().replace("f", "") + "\n" + padText7.uppercase()
                    }
                    soundPool.play(sound7, soundPoolVolume7, soundPoolVolume7, 1, 0, soundPoolTempo7)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view7).findViewById<ImageButton>(
            R.id.tempo_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo7 == 0.125f) {
                        soundPoolTempo7 = 0.2f
                        findViewById<View>(R.id.include_view7).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view7).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume7.toString().replace("f", "") + "            " + soundPoolTempo7.toString().replace("f", "") + "\n" + padText7.uppercase()
                    } else if (soundPoolTempo7 < 8.0f) {
                        soundPoolTempo7 += 0.1f
                        soundPoolTempo7 = "%.1f".format(soundPoolTempo7).toFloat()
                        findViewById<View>(R.id.include_view7).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view7).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume7.toString().replace("f", "") + "            " + soundPoolTempo7.toString().replace("f", "") + "\n" + padText7.uppercase()
                    }
                    soundPool.play(sound7, soundPoolVolume7, soundPoolVolume7, 1, 0, soundPoolTempo7)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view8).findViewById<ImageButton>(
            R.id.volume_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume8 > 0.1f) {
                        soundPoolVolume8 -= 0.1f
                        soundPoolVolume8 = "%.1f".format(soundPoolVolume8).toFloat()
                        findViewById<View>(R.id.include_view8).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view8).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume8.toString().replace("f", "") + "            " + soundPoolTempo8.toString().replace("f", "") + "\n" + padText8.uppercase()
                    }
                    soundPool.play(sound8, soundPoolVolume8, soundPoolVolume8, 1, 0, soundPoolTempo8)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view8).findViewById<ImageButton>(
            R.id.volume_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume8 < 1.0f) {
                        soundPoolVolume8 += 0.1f
                        soundPoolVolume8 = "%.1f".format(soundPoolVolume8).toFloat()
                        findViewById<View>(R.id.include_view8).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view8).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume8.toString().replace("f", "") + "            " + soundPoolTempo8.toString().replace("f", "") + "\n" + padText8.uppercase()
                    }
                    soundPool.play(sound8, soundPoolVolume8, soundPoolVolume8, 1, 0, soundPoolTempo8)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view8).findViewById<ImageButton>(
            R.id.tempo_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo8 > 0.2f) {
                        soundPoolTempo8 -= 0.1f
                        soundPoolTempo8 = "%.1f".format(soundPoolTempo8).toFloat()
                        findViewById<View>(R.id.include_view8).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view8).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume8.toString().replace("f", "") + "            " + soundPoolTempo8.toString().replace("f", "") + "\n" + padText8.uppercase()
                    } else if (soundPoolTempo8 == 0.2f) {
                        soundPoolTempo8 = 0.125f
                        findViewById<View>(R.id.include_view8).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view8).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume8.toString().replace("f", "") + "            " + soundPoolTempo8.toString().replace("f", "") + "\n" + padText8.uppercase()
                    }
                    soundPool.play(sound8, soundPoolVolume8, soundPoolVolume8, 1, 0, soundPoolTempo8)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view8).findViewById<ImageButton>(
            R.id.tempo_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo8 == 0.125f) {
                        soundPoolTempo8 = 0.2f
                        findViewById<View>(R.id.include_view8).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view8).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume8.toString().replace("f", "") + "            " + soundPoolTempo8.toString().replace("f", "") + "\n" + padText8.uppercase()
                    } else if (soundPoolTempo8 < 8.0f) {
                        soundPoolTempo8 += 0.1f
                        soundPoolTempo8 = "%.1f".format(soundPoolTempo8).toFloat()
                        findViewById<View>(R.id.include_view8).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view8).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume8.toString().replace("f", "") + "            " + soundPoolTempo8.toString().replace("f", "") + "\n" + padText8.uppercase()
                    }
                    soundPool.play(sound8, soundPoolVolume8, soundPoolVolume8, 1, 0, soundPoolTempo8)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view9).findViewById<ImageButton>(
            R.id.volume_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume9 > 0.1f) {
                        soundPoolVolume9 -= 0.1f
                        soundPoolVolume9 = "%.1f".format(soundPoolVolume9).toFloat()
                        findViewById<View>(R.id.include_view9).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view9).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume9.toString().replace("f", "") + "            " + soundPoolTempo9.toString().replace("f", "") + "\n" + padText9.uppercase()
                    }
                    soundPool.play(sound9, soundPoolVolume9, soundPoolVolume9, 1, 0, soundPoolTempo9)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view9).findViewById<ImageButton>(
            R.id.volume_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume9 < 1.0f) {
                        soundPoolVolume9 += 0.1f
                        soundPoolVolume9 = "%.1f".format(soundPoolVolume9).toFloat()
                        findViewById<View>(R.id.include_view9).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view9).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume9.toString().replace("f", "") + "            " + soundPoolTempo9.toString().replace("f", "") + "\n" + padText9.uppercase()
                    }
                    soundPool.play(sound9, soundPoolVolume9, soundPoolVolume9, 1, 0, soundPoolTempo9)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view9).findViewById<ImageButton>(
            R.id.tempo_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo9 > 0.2f) {
                        soundPoolTempo9 -= 0.1f
                        soundPoolTempo9 = "%.1f".format(soundPoolTempo9).toFloat()
                        findViewById<View>(R.id.include_view9).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view9).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume9.toString().replace("f", "") + "            " + soundPoolTempo9.toString().replace("f", "") + "\n" + padText9.uppercase()
                    } else if (soundPoolTempo9 == 0.2f) {
                        soundPoolTempo9 = 0.125f
                        findViewById<View>(R.id.include_view9).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view9).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume9.toString().replace("f", "") + "            " + soundPoolTempo9.toString().replace("f", "") + "\n" + padText9.uppercase()
                    }
                    soundPool.play(sound9, soundPoolVolume9, soundPoolVolume9, 1, 0, soundPoolTempo9)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view9).findViewById<ImageButton>(
            R.id.tempo_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo9 == 0.125f) {
                        soundPoolTempo9 = 0.2f
                        findViewById<View>(R.id.include_view9).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view9).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume9.toString().replace("f", "") + "            " + soundPoolTempo9.toString().replace("f", "") + "\n" + padText9.uppercase()
                    } else if (soundPoolTempo9 < 8.0f) {
                        soundPoolTempo9 += 0.1f
                        soundPoolTempo9 = "%.1f".format(soundPoolTempo9).toFloat()
                        findViewById<View>(R.id.include_view9).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view9).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume9.toString().replace("f", "") + "            " + soundPoolTempo9.toString().replace("f", "") + "\n" + padText9.uppercase()
                    }
                    soundPool.play(sound9, soundPoolVolume9, soundPoolVolume9, 1, 0, soundPoolTempo9)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view10).findViewById<ImageButton>(
            R.id.volume_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume10 > 0.1f) {
                        soundPoolVolume10 -= 0.1f
                        soundPoolVolume10 = "%.1f".format(soundPoolVolume10).toFloat()
                        findViewById<View>(R.id.include_view10).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view10).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume10.toString().replace("f", "") + "            " + soundPoolTempo10.toString().replace("f", "") + "\n" + padText10.uppercase()
                    }
                    soundPool.play(sound10, soundPoolVolume10, soundPoolVolume10, 1, 0, soundPoolTempo10)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view10).findViewById<ImageButton>(
            R.id.volume_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume10 < 1.0f) {
                        soundPoolVolume10 += 0.1f
                        soundPoolVolume10 = "%.1f".format(soundPoolVolume10).toFloat()
                        findViewById<View>(R.id.include_view10).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view10).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume10.toString().replace("f", "") + "            " + soundPoolTempo10.toString().replace("f", "") + "\n" + padText10.uppercase()
                    }
                    soundPool.play(sound10, soundPoolVolume10, soundPoolVolume10, 1, 0, soundPoolTempo10)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view10).findViewById<ImageButton>(
            R.id.tempo_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo10 > 0.2f) {
                        soundPoolTempo10 -= 0.1f
                        soundPoolTempo10 = "%.1f".format(soundPoolTempo10).toFloat()
                        findViewById<View>(R.id.include_view10).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view10).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume10.toString().replace("f", "") + "            " + soundPoolTempo10.toString().replace("f", "") + "\n" + padText10.uppercase()
                    } else if (soundPoolTempo10 == 0.2f) {
                        soundPoolTempo10 = 0.125f
                        findViewById<View>(R.id.include_view10).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view10).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume10.toString().replace("f", "") + "            " + soundPoolTempo10.toString().replace("f", "") + "\n" + padText10.uppercase()
                    }
                    soundPool.play(sound10, soundPoolVolume10, soundPoolVolume10, 1, 0, soundPoolTempo10)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view10).findViewById<ImageButton>(
            R.id.tempo_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo10 == 0.125f) {
                        soundPoolTempo10 = 0.2f
                        findViewById<View>(R.id.include_view10).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view10).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume10.toString().replace("f", "") + "            " + soundPoolTempo10.toString().replace("f", "") + "\n" + padText10.uppercase()
                    } else if (soundPoolTempo10 < 8.0f) {
                        soundPoolTempo10 += 0.1f
                        soundPoolTempo10 = "%.1f".format(soundPoolTempo10).toFloat()
                        findViewById<View>(R.id.include_view10).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view10).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume10.toString().replace("f", "") + "            " + soundPoolTempo10.toString().replace("f", "") + "\n" + padText10.uppercase()
                    }
                    soundPool.play(sound10, soundPoolVolume10, soundPoolVolume10, 1, 0, soundPoolTempo10)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view11).findViewById<ImageButton>(
            R.id.volume_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume11 > 0.1f) {
                        soundPoolVolume11 -= 0.1f
                        soundPoolVolume11 = "%.1f".format(soundPoolVolume11).toFloat()
                        findViewById<View>(R.id.include_view11).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view11).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume11.toString().replace("f", "") + "            " + soundPoolTempo11.toString().replace("f", "") + "\n" + padText11.uppercase()
                    }
                    soundPool.play(sound11, soundPoolVolume11, soundPoolVolume11, 1, 0, soundPoolTempo11)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view11).findViewById<ImageButton>(
            R.id.volume_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume11 < 1.0f) {
                        soundPoolVolume11 += 0.1f
                        soundPoolVolume11 = "%.1f".format(soundPoolVolume11).toFloat()
                        findViewById<View>(R.id.include_view11).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view11).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume11.toString().replace("f", "") + "            " + soundPoolTempo11.toString().replace("f", "") + "\n" + padText11.uppercase()
                    }
                    soundPool.play(sound11, soundPoolVolume11, soundPoolVolume11, 1, 0, soundPoolTempo11)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view11).findViewById<ImageButton>(
            R.id.tempo_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo11 > 0.2f) {
                        soundPoolTempo11 -= 0.1f
                        soundPoolTempo11 = "%.1f".format(soundPoolTempo11).toFloat()
                        findViewById<View>(R.id.include_view11).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view11).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume11.toString().replace("f", "") + "            " + soundPoolTempo11.toString().replace("f", "") + "\n" + padText11.uppercase()
                    } else if (soundPoolTempo11 == 0.2f) {
                        soundPoolTempo11 = 0.125f
                        findViewById<View>(R.id.include_view11).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view11).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume11.toString().replace("f", "") + "            " + soundPoolTempo11.toString().replace("f", "") + "\n" + padText11.uppercase()
                    }
                    soundPool.play(sound11, soundPoolVolume11, soundPoolVolume11, 1, 0, soundPoolTempo11)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view11).findViewById<ImageButton>(
            R.id.tempo_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo11 == 0.125f) {
                        soundPoolTempo11 = 0.2f
                        findViewById<View>(R.id.include_view11).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view11).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume11.toString().replace("f", "") + "            " + soundPoolTempo11.toString().replace("f", "") + "\n" + padText11.uppercase()
                    } else if (soundPoolTempo11 < 8.0f) {
                        soundPoolTempo11 += 0.1f
                        soundPoolTempo11 = "%.1f".format(soundPoolTempo11).toFloat()
                        findViewById<View>(R.id.include_view11).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view11).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume11.toString().replace("f", "") + "            " + soundPoolTempo11.toString().replace("f", "") + "\n" + padText11.uppercase()
                    }
                    soundPool.play(sound11, soundPoolVolume11, soundPoolVolume11, 1, 0, soundPoolTempo11)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view12).findViewById<ImageButton>(
            R.id.volume_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume12 > 0.1f) {
                        soundPoolVolume12 -= 0.1f
                        soundPoolVolume12 = "%.1f".format(soundPoolVolume12).toFloat()
                        findViewById<View>(R.id.include_view12).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view12).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume12.toString().replace("f", "") + "            " + soundPoolTempo12.toString().replace("f", "") + "\n" + padText12.uppercase()
                    }
                    soundPool.play(sound12, soundPoolVolume12, soundPoolVolume12, 1, 0, soundPoolTempo12)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view12).findViewById<ImageButton>(
            R.id.volume_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume12 < 1.0f) {
                        soundPoolVolume12 += 0.1f
                        soundPoolVolume12 = "%.1f".format(soundPoolVolume12).toFloat()
                        findViewById<View>(R.id.include_view12).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view12).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume12.toString().replace("f", "") + "            " + soundPoolTempo12.toString().replace("f", "") + "\n" + padText12.uppercase()
                    }
                    soundPool.play(sound12, soundPoolVolume12, soundPoolVolume12, 1, 0, soundPoolTempo12)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view12).findViewById<ImageButton>(
            R.id.tempo_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo12 > 0.2f) {
                        soundPoolTempo12 -= 0.1f
                        soundPoolTempo12 = "%.1f".format(soundPoolTempo12).toFloat()
                        findViewById<View>(R.id.include_view12).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view12).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume12.toString().replace("f", "") + "            " + soundPoolTempo12.toString().replace("f", "") + "\n" + padText12.uppercase()
                    } else if (soundPoolTempo12 == 0.2f) {
                        soundPoolTempo12 = 0.125f
                        findViewById<View>(R.id.include_view12).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view12).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume12.toString().replace("f", "") + "            " + soundPoolTempo12.toString().replace("f", "") + "\n" + padText12.uppercase()
                    }
                    soundPool.play(sound12, soundPoolVolume12, soundPoolVolume12, 1, 0, soundPoolTempo12)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view12).findViewById<ImageButton>(
            R.id.tempo_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo12 == 0.125f) {
                        soundPoolTempo12 = 0.2f
                        findViewById<View>(R.id.include_view12).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view12).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume12.toString().replace("f", "") + "            " + soundPoolTempo12.toString().replace("f", "") + "\n" + padText12.uppercase()
                    } else if (soundPoolTempo12 < 8.0f) {
                        soundPoolTempo12 += 0.1f
                        soundPoolTempo12 = "%.1f".format(soundPoolTempo12).toFloat()
                        findViewById<View>(R.id.include_view12).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view12).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume12.toString().replace("f", "") + "            " + soundPoolTempo12.toString().replace("f", "") + "\n" + padText12.uppercase()
                    }
                    soundPool.play(sound12, soundPoolVolume12, soundPoolVolume12, 1, 0, soundPoolTempo12)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view13).findViewById<ImageButton>(
            R.id.volume_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume13 > 0.1f) {
                        soundPoolVolume13 -= 0.1f
                        soundPoolVolume13 = "%.1f".format(soundPoolVolume13).toFloat()
                        findViewById<View>(R.id.include_view13).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view13).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume13.toString().replace("f", "") + "            " + soundPoolTempo13.toString().replace("f", "") + "\n" + padText13.uppercase()
                    }
                    soundPool.play(sound13, soundPoolVolume13, soundPoolVolume13, 1, 0, soundPoolTempo13)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view13).findViewById<ImageButton>(
            R.id.volume_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume13 < 1.0f) {
                        soundPoolVolume13 += 0.1f
                        soundPoolVolume13 = "%.1f".format(soundPoolVolume13).toFloat()
                        findViewById<View>(R.id.include_view13).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view13).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume13.toString().replace("f", "") + "            " + soundPoolTempo13.toString().replace("f", "") + "\n" + padText13.uppercase()
                    }
                    soundPool.play(sound13, soundPoolVolume13, soundPoolVolume13, 1, 0, soundPoolTempo13)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view13).findViewById<ImageButton>(
            R.id.tempo_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo13 > 0.2f) {
                        soundPoolTempo13 -= 0.1f
                        soundPoolTempo13 = "%.1f".format(soundPoolTempo13).toFloat()
                        findViewById<View>(R.id.include_view13).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view13).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume13.toString().replace("f", "") + "            " + soundPoolTempo13.toString().replace("f", "") + "\n" + padText13.uppercase()
                    } else if (soundPoolTempo13 == 0.2f) {
                        soundPoolTempo13 = 0.125f
                        findViewById<View>(R.id.include_view13).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view13).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume13.toString().replace("f", "") + "            " + soundPoolTempo13.toString().replace("f", "") + "\n" + padText13.uppercase()
                    }
                    soundPool.play(sound13, soundPoolVolume13, soundPoolVolume13, 1, 0, soundPoolTempo13)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view13).findViewById<ImageButton>(
            R.id.tempo_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo13 == 0.125f) {
                        soundPoolTempo13 = 0.2f
                        findViewById<View>(R.id.include_view13).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view13).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume13.toString().replace("f", "") + "            " + soundPoolTempo13.toString().replace("f", "") + "\n" + padText13.uppercase()
                    } else if (soundPoolTempo13 < 8.0f) {
                        soundPoolTempo13 += 0.1f
                        soundPoolTempo13 = "%.1f".format(soundPoolTempo13).toFloat()
                        findViewById<View>(R.id.include_view13).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view13).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume13.toString().replace("f", "") + "            " + soundPoolTempo13.toString().replace("f", "") + "\n" + padText13.uppercase()
                    }
                    soundPool.play(sound13, soundPoolVolume13, soundPoolVolume13, 1, 0, soundPoolTempo13)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view14).findViewById<ImageButton>(
            R.id.volume_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume14 > 0.1f) {
                        soundPoolVolume14 -= 0.1f
                        soundPoolVolume14 = "%.1f".format(soundPoolVolume14).toFloat()
                        findViewById<View>(R.id.include_view14).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view14).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume14.toString().replace("f", "") + "            " + soundPoolTempo14.toString().replace("f", "") + "\n" + padText14.uppercase()
                    }
                    soundPool.play(sound14, soundPoolVolume14, soundPoolVolume14, 1, 0, soundPoolTempo14)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view14).findViewById<ImageButton>(
            R.id.volume_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume14 < 1.0f) {
                        soundPoolVolume14 += 0.1f
                        soundPoolVolume14 = "%.1f".format(soundPoolVolume14).toFloat()
                        findViewById<View>(R.id.include_view14).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view14).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume14.toString().replace("f", "") + "            " + soundPoolTempo14.toString().replace("f", "") + "\n" + padText14.uppercase()
                    }
                    soundPool.play(sound14, soundPoolVolume14, soundPoolVolume14, 1, 0, soundPoolTempo14)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view14).findViewById<ImageButton>(
            R.id.tempo_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo14 > 0.2f) {
                        soundPoolTempo14 -= 0.1f
                        soundPoolTempo14 = "%.1f".format(soundPoolTempo14).toFloat()
                        findViewById<View>(R.id.include_view14).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view14).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume14.toString().replace("f", "") + "            " + soundPoolTempo14.toString().replace("f", "") + "\n" + padText14.uppercase()
                    } else if (soundPoolTempo14 == 0.2f) {
                        soundPoolTempo14 = 0.125f
                        findViewById<View>(R.id.include_view14).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view14).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume14.toString().replace("f", "") + "            " + soundPoolTempo14.toString().replace("f", "") + "\n" + padText14.uppercase()
                    }
                    soundPool.play(sound14, soundPoolVolume14, soundPoolVolume14, 1, 0, soundPoolTempo14)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view14).findViewById<ImageButton>(
            R.id.tempo_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo14 == 0.125f) {
                        soundPoolTempo14 = 0.2f
                        findViewById<View>(R.id.include_view14).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view14).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume14.toString().replace("f", "") + "            " + soundPoolTempo14.toString().replace("f", "") + "\n" + padText14.uppercase()
                    } else if (soundPoolTempo14 < 8.0f) {
                        soundPoolTempo14 += 0.1f
                        soundPoolTempo14 = "%.1f".format(soundPoolTempo14).toFloat()
                        findViewById<View>(R.id.include_view14).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view14).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume14.toString().replace("f", "") + "            " + soundPoolTempo14.toString().replace("f", "") + "\n" + padText14.uppercase()
                    }
                    soundPool.play(sound14, soundPoolVolume14, soundPoolVolume14, 1, 0, soundPoolTempo14)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view15).findViewById<ImageButton>(
            R.id.volume_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume15 > 0.1f) {
                        soundPoolVolume15 -= 0.1f
                        soundPoolVolume15 = "%.1f".format(soundPoolVolume15).toFloat()
                        findViewById<View>(R.id.include_view15).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view15).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume15.toString().replace("f", "") + "            " + soundPoolTempo15.toString().replace("f", "") + "\n" + padText15.uppercase()
                    }
                    soundPool.play(sound15, soundPoolVolume15, soundPoolVolume15, 1, 0, soundPoolTempo15)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view15).findViewById<ImageButton>(
            R.id.volume_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolVolume15 < 1.0f) {
                        soundPoolVolume15 += 0.1f
                        soundPoolVolume15 = "%.1f".format(soundPoolVolume15).toFloat()
                        findViewById<View>(R.id.include_view15).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view15).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume15.toString().replace("f", "") + "            " + soundPoolTempo15.toString().replace("f", "") + "\n" + padText15.uppercase()
                    }
                    soundPool.play(sound15, soundPoolVolume15, soundPoolVolume15, 1, 0, soundPoolTempo15)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view15).findViewById<ImageButton>(
            R.id.tempo_minus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo15 > 0.2f) {
                        soundPoolTempo15 -= 0.1f
                        soundPoolTempo15 = "%.1f".format(soundPoolTempo15).toFloat()
                        findViewById<View>(R.id.include_view15).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view15).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume15.toString().replace("f", "") + "            " + soundPoolTempo15.toString().replace("f", "") + "\n" + padText15.uppercase()
                    } else if (soundPoolTempo15 == 0.2f) {
                        soundPoolTempo15 = 0.125f
                        findViewById<View>(R.id.include_view15).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view15).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume15.toString().replace("f", "") + "            " + soundPoolTempo15.toString().replace("f", "") + "\n" + padText15.uppercase()
                    }
                    soundPool.play(sound15, soundPoolVolume15, soundPoolVolume15, 1, 0, soundPoolTempo15)
                }
            }
            false
        }
        findViewById<View>(R.id.include_view15).findViewById<ImageButton>(
            R.id.tempo_plus).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (soundPoolTempo15 == 0.125f) {
                        soundPoolTempo15 = 0.2f
                        findViewById<View>(R.id.include_view15).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view15).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume15.toString().replace("f", "") + "            " + soundPoolTempo15.toString().replace("f", "") + "\n" + padText15.uppercase()
                    } else if (soundPoolTempo15 < 8.0f) {
                        soundPoolTempo15 += 0.1f
                        soundPoolTempo15 = "%.1f".format(soundPoolTempo15).toFloat()
                        findViewById<View>(R.id.include_view15).findViewById<TextView>(
                            R.id.padText).text = ""
                        findViewById<View>(R.id.include_view15).findViewById<TextView>(
                            R.id.padText).text = soundPoolVolume15.toString().replace("f", "") + "            " + soundPoolTempo15.toString().replace("f", "") + "\n" + padText15.uppercase()
                    }
                    soundPool.play(sound15, soundPoolVolume15, soundPoolVolume15, 1, 0, soundPoolTempo15)
                }
            }
            false
        }
    }

    private fun stickyImmersiveMode() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        decorView.setOnSystemUiVisibilityChangeListener { visibility -> // Note that system bars will only be "visible" if none of the
            // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                Log.d("debug", "The system bars are visible")
            } else {
                Log.d("debug", "The system bars are NOT visible")
            }
        }
    }

    private fun loadRewardedAd() {
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917", adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("rewarded ads", adError.message)
                mRewardedAd = null
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                Log.d("rewarded ads", "Ad was loaded.")
                mRewardedAd = rewardedAd
            }
        })

        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d("rewarded ads", "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d("rewarded ads", "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d("rewarded ads", "Ad showed fullscreen content.")
                mRewardedAd = null
            }
        }
    }

    private fun showRewardAd() {
        if (mRewardedAd != null) {
            mRewardedAd?.show(this) { rewardItem ->
                binding.adView.visibility = View.GONE
                binding.bottomSpace.visibility = View.GONE
                binding.gridView.visibility = View.INVISIBLE
                adCheck = 1
                stickyImmersiveMode()
                var rewardAmount = rewardItem.amount
                var rewardType = rewardItem.type
                Log.d("TAG", rewardItem.toString())
                Log.d("TAG", "User earned the reward.")
            }
        } else {
            stickyImmersiveMode()
            Log.d("TAG", "The rewarded ad wasn't ready yet.")
        }
    }

    private val adSize: AdSize
        get() {
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density
            var adWidthPixels = adViewContainer.width.toFloat()
            if (adWidthPixels == 0.0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }
            val adWidth = (adWidthPixels / density).toInt()


            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this@MainActivity, adWidth)
        }

    private fun initAdMob() {
        adViewContainer = findViewById(R.id.adView)

        MobileAds.initialize(this) {}
        admobmAdView = AdView(this)
        admobmAdView.adUnitId = "ca-app-pub-3940256099942544/6300978111"

        admobmAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                adViewContainer.addView(admobmAdView)
            }
        }
    }

    private fun loadAdMob() {
        val request = AdRequest.Builder().build()
        admobmAdView.adSize = adSize
        admobmAdView.loadAd(request)
    }

    private fun effect(imageView: ImageView, mpDuration: Int) {
        val cx = imageView.width / 2
        val cy = imageView.height / 2

        val initialRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(imageView, cx, cy, initialRadius, 0f)

        anim.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                imageView.setColorFilter(Color.parseColor("#e2e3e3"), PorterDuff.Mode.SRC_IN)
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                imageView.setColorFilter(Color.parseColor("#e2e3e3"), PorterDuff.Mode.SRC_IN)
            }
        })

        anim.duration = mpDuration.toLong()
        anim.start()
    }

    override fun clicked(soundList: SoundList) {
        sound16 = if (buttonB == 1) {
            soundPool.load(soundList.name, 1)
        } else {
            soundPool.load(assets.openFd(soundList.name), 1)
        }
            soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f)
            }
    }

    @SuppressLint("SetTextI18n")
    override fun clicked2(soundList: SoundList) {
        try {
            when {
                buttonA == 1 && buttonB == 1 -> {
                    effect(binding.imageView,400)
                    sound1 = soundPool.load(soundList.name, 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(this, Uri.parse(soundList.name))
                    getmpDuration.prepare()
                    mpDuration = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView.text = soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText1 = " " + soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume = 0.5f
                    soundPoolTempo = 1.0f
                    findViewById<View>(R.id.include_view).findViewById<TextView>(
                        R.id.padText).text = padText1
                }
                buttonA == 2 && buttonB == 1 -> {
                    effect(binding.imageView2,400)
                    sound2 = soundPool.load(soundList.name, 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(this, Uri.parse(soundList.name))
                    getmpDuration.prepare()
                    mpDuration2 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView2.text = soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText2 = " " + soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume2 = 0.5f
                    soundPoolTempo2 = 1.0f
                    findViewById<View>(R.id.include_view2).findViewById<TextView>(
                        R.id.padText).text = padText2
                }
                buttonA == 3 && buttonB == 1 -> {
                    effect(binding.imageView3,400)
                    sound3 = soundPool.load(soundList.name, 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(this, Uri.parse(soundList.name))
                    getmpDuration.prepare()
                    mpDuration3 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView3.text = soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText3 = " " + soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume3 = 0.5f
                    soundPoolTempo3 = 1.0f
                    findViewById<View>(R.id.include_view3).findViewById<TextView>(
                        R.id.padText).text = padText3
                }
                buttonA == 4 && buttonB == 1 -> {
                    effect(binding.imageView4,400)
                    sound4 = soundPool.load(soundList.name, 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(this, Uri.parse(soundList.name))
                    getmpDuration.prepare()
                    mpDuration4 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView4.text = soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText4 = " " + soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume4 = 0.5f
                    soundPoolTempo4 = 1.0f
                    findViewById<View>(R.id.include_view4).findViewById<TextView>(
                        R.id.padText).text = padText4
                }
                buttonA == 5 && buttonB == 1 -> {
                    effect(binding.imageView5,400)
                    sound5 = soundPool.load(soundList.name, 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(this, Uri.parse(soundList.name))
                    getmpDuration.prepare()
                    mpDuration5 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView5.text = soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText5 = " " + soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume5 = 0.5f
                    soundPoolTempo5 = 1.0f
                    findViewById<View>(R.id.include_view5).findViewById<TextView>(
                        R.id.padText).text = padText5
                }
                buttonA == 6 && buttonB == 1 -> {
                    effect(binding.imageView6,400)
                    sound6 = soundPool.load(soundList.name, 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(this, Uri.parse(soundList.name))
                    getmpDuration.prepare()
                    mpDuration6 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView6.text = soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText6 = " " + soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume6 = 0.5f
                    soundPoolTempo6 = 1.0f
                    findViewById<View>(R.id.include_view6).findViewById<TextView>(
                        R.id.padText).text = padText6
                }
                buttonA == 7 && buttonB == 1 -> {
                    effect(binding.imageView7,400)
                    sound7 = soundPool.load(soundList.name, 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(this, Uri.parse(soundList.name))
                    getmpDuration.prepare()
                    mpDuration7 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView7.text = soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText7 = " " + soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume7 = 0.5f
                    soundPoolTempo7 = 1.0f
                    findViewById<View>(R.id.include_view7).findViewById<TextView>(
                        R.id.padText).text = padText7
                }
                buttonA == 8 && buttonB == 1 -> {
                    effect(binding.imageView8,400)
                    sound8 = soundPool.load(soundList.name, 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(this, Uri.parse(soundList.name))
                    getmpDuration.prepare()
                    mpDuration8 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView8.text = soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText8 = " " + soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume8 = 0.5f
                    soundPoolTempo8 = 1.0f
                    findViewById<View>(R.id.include_view8).findViewById<TextView>(
                        R.id.padText).text = padText8
                }
                buttonA == 9 && buttonB == 1 -> {
                    effect(binding.imageView9,400)
                    sound9 = soundPool.load(soundList.name, 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(this, Uri.parse(soundList.name))
                    getmpDuration.prepare()
                    mpDuration9 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView9.text = soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText9 = " " + soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume9 = 0.5f
                    soundPoolTempo9 = 1.0f
                    findViewById<View>(R.id.include_view9).findViewById<TextView>(
                        R.id.padText).text = padText9
                }
                buttonA == 10 && buttonB == 1 -> {
                    effect(binding.imageView10,400)
                    sound10 = soundPool.load(soundList.name, 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(this, Uri.parse(soundList.name))
                    getmpDuration.prepare()
                    mpDuration10 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView10.text = soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText10 = " " + soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume10 = 0.5f
                    soundPoolTempo10 = 1.0f
                    findViewById<View>(R.id.include_view10).findViewById<TextView>(
                        R.id.padText).text = padText10
                }
                buttonA == 11 && buttonB == 1 -> {
                    effect(binding.imageView11,400)
                    sound11 = soundPool.load(soundList.name, 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(this, Uri.parse(soundList.name))
                    getmpDuration.prepare()
                    mpDuration11 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView11.text = soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText11 = " " + soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume11 = 0.5f
                    soundPoolTempo11 = 1.0f
                    findViewById<View>(R.id.include_view11).findViewById<TextView>(
                        R.id.padText).text = padText11
                }
                buttonA == 12 && buttonB == 1 -> {
                    effect(binding.imageView12,400)
                    sound12 = soundPool.load(soundList.name, 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(this, Uri.parse(soundList.name))
                    getmpDuration.prepare()
                    mpDuration12 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView12.text = soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText12 = " " + soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume12 = 0.5f
                    soundPoolTempo12 = 1.0f
                    findViewById<View>(R.id.include_view12).findViewById<TextView>(
                        R.id.padText).text = padText12
                }
                buttonA == 13 && buttonB == 1 -> {
                    effect(binding.imageView13,400)
                    sound13 = soundPool.load(soundList.name, 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(this, Uri.parse(soundList.name))
                    getmpDuration.prepare()
                    mpDuration13 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView13.text = soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText13 = " " + soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume13 = 0.5f
                    soundPoolTempo13 = 1.0f
                    findViewById<View>(R.id.include_view13).findViewById<TextView>(
                        R.id.padText).text = padText13
                }
                buttonA == 14 && buttonB == 1 -> {
                    effect(binding.imageView14,400)
                    sound14 = soundPool.load(soundList.name, 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(this, Uri.parse(soundList.name))
                    getmpDuration.prepare()
                    mpDuration14 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView14.text = soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText14 = " " + soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume14 = 0.5f
                    soundPoolTempo14 = 1.0f
                    findViewById<View>(R.id.include_view14).findViewById<TextView>(
                        R.id.padText).text = padText14
                }
                buttonA == 15 && buttonB == 1 -> {
                    effect(binding.imageView15,400)
                    sound15 = soundPool.load(soundList.name, 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(this, Uri.parse(soundList.name))
                    getmpDuration.prepare()
                    mpDuration15 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView15.text = soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText15 = " " + soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume15 = 0.5f
                    soundPoolTempo15 = 1.0f
                    findViewById<View>(R.id.include_view15).findViewById<TextView>(
                        R.id.padText).text = padText15
                }
                buttonA == 16 && buttonB == 1 -> {
                    lmp.release()
                    lmp = LoopMediaPlayer(this@MainActivity, Uri.parse(soundList.name))
                    lmp.stop()
                    count = 0.5f
                    bpm = 1.0f
                    actionTitle = soundList.name.replaceBeforeLast("/", "").replace("/", "")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase() + " loop"
                    supportActionBar?.title = actionTitle
                    if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        findViewById<TextView>(R.id.padText0).text = count.toString().replace("f", "") + " " + actionTitle + " " + bpm.toString().replace("f", "").uppercase()
                    } else if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        findViewById<TextView>(R.id.padText0).text = count.toString().replace("f", "") + "\n\n" + "loop" + "\n\n" + bpm.toString().replace("f", "").uppercase()
                    }
                    soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                }
                buttonA == 1 && buttonB == 2 -> {
                    effect(binding.imageView,400)
                    sound1 = soundPool.load(assets.openFd(soundList.name), 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(assets.openFd(soundList.name).fileDescriptor,
                        assets.openFd(soundList.name).startOffset,
                        assets.openFd(soundList.name).declaredLength)
                    getmpDuration.prepare()
                    mpDuration = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView.text = " " + soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText1 = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume = 0.5f
                    soundPoolTempo = 1.0f
                    findViewById<View>(R.id.include_view).findViewById<TextView>(
                        R.id.padText).text = padText1
                }
                buttonA == 2 && buttonB == 2 -> {
                    effect(binding.imageView2,400)
                    sound2 = soundPool.load(assets.openFd(soundList.name), 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(assets.openFd(soundList.name).fileDescriptor,
                        assets.openFd(soundList.name).startOffset,
                        assets.openFd(soundList.name).declaredLength)
                    getmpDuration.prepare()
                    mpDuration2 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView2.text = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText2 = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume2 = 0.5f
                    soundPoolTempo2 = 1.0f
                    findViewById<View>(R.id.include_view2).findViewById<TextView>(
                        R.id.padText).text = padText2
                }
                buttonA == 3 && buttonB == 2 -> {
                    effect(binding.imageView3,400)
                    sound3 = soundPool.load(assets.openFd(soundList.name), 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(assets.openFd(soundList.name).fileDescriptor,
                        assets.openFd(soundList.name).startOffset,
                        assets.openFd(soundList.name).declaredLength)
                    getmpDuration.prepare()
                    mpDuration3 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView3.text = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText3 = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume3 = 0.5f
                    soundPoolTempo3 = 1.0f
                    findViewById<View>(R.id.include_view3).findViewById<TextView>(
                        R.id.padText).text = padText3
                }
                buttonA == 4 && buttonB == 2 -> {
                    effect(binding.imageView4,400)
                    sound4 = soundPool.load(assets.openFd(soundList.name), 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(assets.openFd(soundList.name).fileDescriptor,
                        assets.openFd(soundList.name).startOffset,
                        assets.openFd(soundList.name).declaredLength)
                    getmpDuration.prepare()
                    mpDuration4 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView4.text = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText4 = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume4 = 0.5f
                    soundPoolTempo4 = 1.0f
                    findViewById<View>(R.id.include_view4).findViewById<TextView>(
                        R.id.padText).text = padText4
                }
                buttonA == 5 && buttonB == 2 -> {
                    effect(binding.imageView5,400)
                    sound5 = soundPool.load(assets.openFd(soundList.name), 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(assets.openFd(soundList.name).fileDescriptor,
                        assets.openFd(soundList.name).startOffset,
                        assets.openFd(soundList.name).declaredLength)
                    getmpDuration.prepare()
                    mpDuration5 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView5.text = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText5 = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume5 = 0.5f
                    soundPoolTempo5 = 1.0f
                    findViewById<View>(R.id.include_view5).findViewById<TextView>(
                        R.id.padText).text = padText5
                }
                buttonA == 6 && buttonB == 2 -> {
                    effect(binding.imageView6,400)
                    sound6 = soundPool.load(assets.openFd(soundList.name), 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(assets.openFd(soundList.name).fileDescriptor,
                        assets.openFd(soundList.name).startOffset,
                        assets.openFd(soundList.name).declaredLength)
                    getmpDuration.prepare()
                    mpDuration6 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView6.text = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText6 = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume6 = 0.5f
                    soundPoolTempo6 = 1.0f
                    findViewById<View>(R.id.include_view6).findViewById<TextView>(
                        R.id.padText).text = padText6
                }
                buttonA == 7 && buttonB == 2 -> {
                    effect(binding.imageView7,400)
                    sound7 = soundPool.load(assets.openFd(soundList.name), 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(assets.openFd(soundList.name).fileDescriptor,
                        assets.openFd(soundList.name).startOffset,
                        assets.openFd(soundList.name).declaredLength)
                    getmpDuration.prepare()
                    mpDuration7 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView7.text = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText7 = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume7 = 0.5f
                    soundPoolTempo7 = 1.0f
                    findViewById<View>(R.id.include_view7).findViewById<TextView>(
                        R.id.padText).text = padText7
                }
                buttonA == 8 && buttonB == 2 -> {
                    effect(binding.imageView8,400)
                    sound8 = soundPool.load(assets.openFd(soundList.name), 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(assets.openFd(soundList.name).fileDescriptor,
                        assets.openFd(soundList.name).startOffset,
                        assets.openFd(soundList.name).declaredLength)
                    getmpDuration.prepare()
                    mpDuration8 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView8.text = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText8 = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume8 = 0.5f
                    soundPoolTempo8 = 1.0f
                    findViewById<View>(R.id.include_view8).findViewById<TextView>(
                        R.id.padText).text = padText8
                }
                buttonA == 9 && buttonB == 2 -> {
                    effect(binding.imageView9,400)
                    sound9 = soundPool.load(assets.openFd(soundList.name), 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(assets.openFd(soundList.name).fileDescriptor,
                        assets.openFd(soundList.name).startOffset,
                        assets.openFd(soundList.name).declaredLength)
                    getmpDuration.prepare()
                    mpDuration9 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView9.text = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText9 = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume9 = 0.5f
                    soundPoolTempo9 = 1.0f
                    findViewById<View>(R.id.include_view9).findViewById<TextView>(
                        R.id.padText).text = padText9
                }
                buttonA == 10 && buttonB == 2 -> {
                    effect(binding.imageView10,400)
                    sound10 = soundPool.load(assets.openFd(soundList.name), 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(assets.openFd(soundList.name).fileDescriptor,
                        assets.openFd(soundList.name).startOffset,
                        assets.openFd(soundList.name).declaredLength)
                    getmpDuration.prepare()
                    mpDuration10 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView10.text = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText10 = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume10 = 0.5f
                    soundPoolTempo10 = 1.0f
                    findViewById<View>(R.id.include_view10).findViewById<TextView>(
                        R.id.padText).text = padText10
                }
                buttonA == 11 && buttonB == 2 -> {
                    effect(binding.imageView11,400)
                    sound11 = soundPool.load(assets.openFd(soundList.name), 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(assets.openFd(soundList.name).fileDescriptor,
                        assets.openFd(soundList.name).startOffset,
                        assets.openFd(soundList.name).declaredLength)
                    getmpDuration.prepare()
                    mpDuration11 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView11.text = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText11 = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume11 = 0.5f
                    soundPoolTempo11 = 1.0f
                    findViewById<View>(R.id.include_view11).findViewById<TextView>(
                        R.id.padText).text = padText11
                }
                buttonA == 12 && buttonB == 2 -> {
                    effect(binding.imageView12,400)
                    sound12 = soundPool.load(assets.openFd(soundList.name), 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(assets.openFd(soundList.name).fileDescriptor,
                        assets.openFd(soundList.name).startOffset,
                        assets.openFd(soundList.name).declaredLength)
                    getmpDuration.prepare()
                    mpDuration12 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView12.text = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText12 = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume12 = 0.5f
                    soundPoolTempo12 = 1.0f
                    findViewById<View>(R.id.include_view12).findViewById<TextView>(
                        R.id.padText).text = padText12
                }
                buttonA == 13 && buttonB == 2 -> {
                    effect(binding.imageView13,400)
                    sound13 = soundPool.load(assets.openFd(soundList.name), 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(assets.openFd(soundList.name).fileDescriptor,
                        assets.openFd(soundList.name).startOffset,
                        assets.openFd(soundList.name).declaredLength)
                    getmpDuration.prepare()
                    mpDuration13 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView13.text = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText13 = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume13 = 0.5f
                    soundPoolTempo13 = 1.0f
                    findViewById<View>(R.id.include_view13).findViewById<TextView>(
                        R.id.padText).text = padText13
                }
                buttonA == 14 && buttonB == 2 -> {
                    effect(binding.imageView14,400)
                    sound14 = soundPool.load(assets.openFd(soundList.name), 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(assets.openFd(soundList.name).fileDescriptor,
                        assets.openFd(soundList.name).startOffset,
                        assets.openFd(soundList.name).declaredLength)
                    getmpDuration.prepare()
                    mpDuration14 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView14.text = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText14 = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume14 = 0.5f
                    soundPoolTempo14 = 1.0f
                    findViewById<View>(R.id.include_view14).findViewById<TextView>(
                        R.id.padText).text = padText14
                }
                buttonA == 15 && buttonB == 2 -> {
                    effect(binding.imageView15,400)
                    sound15 = soundPool.load(assets.openFd(soundList.name), 1)
                    getmpDuration = MediaPlayer()
                    getmpDuration.setDataSource(assets.openFd(soundList.name).fileDescriptor,
                        assets.openFd(soundList.name).startOffset,
                        assets.openFd(soundList.name).declaredLength)
                    getmpDuration.prepare()
                    mpDuration15 = getmpDuration.duration
                    getmpDuration.release()
                    soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                    binding.textView15.text = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase()
                    padText15 = soundList.name.replace("tr_8", "TR-8").replace("tr_909", "TR-909")
                        .replaceAfterLast(".", "").replace("_", " ").replace(".","").uppercase()
                    soundPoolVolume15 = 0.5f
                    soundPoolTempo15 = 1.0f
                    findViewById<View>(R.id.include_view15).findViewById<TextView>(
                        R.id.padText).text = padText15
                }
                buttonA == 16 -> {
                    lmp.release()
                    lmp = LoopMediaPlayer(this@MainActivity, Uri.parse("android.resource://" + packageName + "/raw/" + soundList.name.replace(".ogg", "")))
                    lmp.stop()
                    count = 0.5f
                    bpm = 1.0f
                    actionTitle = soundList.name.replaceAfterLast(".", "").replace("_", " ").replace("."," ").uppercase() + " loop"
                    supportActionBar?.title = actionTitle
                    if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        findViewById<TextView>(R.id.padText0).text = count.toString().replace("f", "") + " " + actionTitle + " " + bpm.toString().replace("f", "").uppercase()
                    } else if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        findViewById<TextView>(R.id.padText0).text = count.toString().replace("f", "") + "\n\n" + "loop" + "\n\n" + bpm.toString().replace("f", "").uppercase()
                    }
                    soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                        soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                    }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(applicationContext, R.string.error, Toast.LENGTH_LONG).show()
        }
        findViewById<ListView>(R.id.list_view).visibility = View.INVISIBLE
    }

    private fun isReadExternalStoragePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestReadExternalStoragePermission() {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                RECORD_AUDIO_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RECORD_AUDIO_PERMISSION_REQUEST_CODE) {
            if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                stickyImmersiveMode()
                Toast.makeText(
                        this,
                        R.string.onRequestPermissionsResult1,
                        Toast.LENGTH_LONG
                ).show()
            } else {
                stickyImmersiveMode()
                Toast.makeText(
                        this,
                        R.string.onRequestPermissionsResult2,
                        Toast.LENGTH_LONG
                ).show()
            }
        }

        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                stickyImmersiveMode()
                Toast.makeText(
                        this,
                        R.string.onRequestPermissionsResult1,
                        Toast.LENGTH_LONG
                ).show()
            } else {
                stickyImmersiveMode()
                Toast.makeText(
                        this,
                        R.string.onRequestPermissionsResult2,
                        Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val menuLamp = menu!!.findItem(R.id.menu1)
        if (menuSwitch) {
            menuLamp.setIcon(R.drawable.ic_baseline_play_arrow_24)
        } else {
            menuLamp.setIcon(R.drawable.ic_baseline_stop_24)
        }

        val menuLamp2 = menu.findItem(R.id.menu10)
        if (paste == 1) {
            menuLamp2.setIcon(R.drawable.ic_baseline_library_music_24_c)
        } else if (paste == 2) {
            menuLamp2.setIcon(R.drawable.ic_baseline_library_music_24)
        }

        return true
    }

    private var menuSwitch = true
    private var menuSwitch2 = true
    private var switch1 = 0


    @SuppressLint("SimpleDateFormat")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val soundListView = findViewById<ListView>(R.id.list_view)
        val actionGridView = findViewById<GridView>(R.id.grid_view)
        val chooseGridView = findViewById<GridView>(R.id.grid_view_choose)
        val tuningView = findViewById<View>(R.id.view)

        when (item.itemId) {

            R.id.menu1 -> {
                when {
                    soundListView.isVisible -> {
                        soundListView.visibility = View.INVISIBLE
                    }
                    actionGridView.isVisible -> {
                        actionGridView.visibility = View.INVISIBLE
                    }
                    chooseGridView.isVisible -> {
                        chooseGridView.visibility = View.INVISIBLE
                    }
                }
                if (switch1 == 1) {
                    lmp.stop()
                    soundPool.autoPause()
                    menuSwitch = true
                    invalidateOptionsMenu()
                    switch1 = 2
                } else {
                    lmp.start()
                    menuSwitch = false
                    invalidateOptionsMenu()
                    switch1 = 1
                }
                return true
            }

            R.id.menu10 -> {
                when {
                    chooseGridView.isVisible -> {
                        actionGridView.visibility = View.INVISIBLE
                        chooseGridView.visibility = View.INVISIBLE
                        tuningView.visibility = View.INVISIBLE
                    }
                    soundListView.isVisible -> {
                        chooseGridView.visibility = View.VISIBLE
                        soundListView.visibility = View.INVISIBLE
                    }
                    actionGridView.isVisible -> {
                        chooseGridView.visibility = View.VISIBLE
                        actionGridView.visibility = View.INVISIBLE
                        tuningView.visibility = View.INVISIBLE
                    }
                    tuningView.isVisible -> {
                        chooseGridView.visibility = View.VISIBLE
                        actionGridView.visibility = View.INVISIBLE
                        tuningView.visibility = View.INVISIBLE
                    }
                    soundListView.isInvisible && actionGridView.isInvisible && tuningView.isInvisible -> {
                        chooseGridView.visibility = View.VISIBLE
                    }
                }
                return true
            }

            R.id.action_settings -> {
                when {
                    chooseGridView.isVisible -> {
                        actionGridView.visibility = View.VISIBLE
                        chooseGridView.visibility = View.INVISIBLE
                        tuningView.visibility = View.INVISIBLE
                    }
                    soundListView.isVisible -> {
                        actionGridView.visibility = View.VISIBLE
                        soundListView.visibility = View.INVISIBLE
                    }
                    actionGridView.isInvisible && tuningView.isVisible -> {
                        tuningView.visibility = View.INVISIBLE
                    }
                    actionGridView.isInvisible -> {
                        actionGridView.visibility = View.VISIBLE
                    }
                    actionGridView.isVisible -> {
                        actionGridView.visibility = View.INVISIBLE
                        tuningView.visibility = View.INVISIBLE
                    }
                }
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        lmp.reset()
        lmp.release()
        mp.reset()
        mp.release()
        soundPool.autoPause()
        soundPool.release()

        super.onDestroy()
    }

    override fun onPause() {
        menuSwitch = true
        invalidateOptionsMenu()
        switch1 = 2
        if (mp.isPlaying) {
            mp.stop()
            mp.prepare()
        }
        if (!menuSwitch2) {
            menuSwitch2 = true
            invalidateOptionsMenu()
        }

            lmp.stop()
            soundPool.autoPause()

        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("DATA", adCheck)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        adCheck = savedInstanceState.getInt("DATA")
        if ( adCheck == 1) {
            binding.adView.visibility = View.GONE
            binding.bottomSpace.visibility = View.GONE
        }
    }
}
