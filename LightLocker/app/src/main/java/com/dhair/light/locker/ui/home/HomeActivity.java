package com.dhair.light.locker.ui.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.android.common.util.ToastUtils;
import com.android.process.library.processutil.AndroidProcesses;
import com.android.ui.ripple.RippleView;
import com.dhair.light.locker.R;
import com.dhair.light.locker.component.thread.CustomHandlerThread;
import com.dhair.light.locker.ui.abs.AbsMvpActivity;
import com.dhair.light.locker.ui.home.presenter.HomePresenter;
import com.dhair.light.locker.utils.channel.ChannelUtils;

import butterknife.BindView;

public class HomeActivity extends AbsMvpActivity<HomePresenter> {

    @BindView(R.id.test_text)
    RippleView mTestText;

    @BindView(R.id.text1)
    TextView mText;

    @Override
    protected void onPreSetContentView() {
        super.onPreSetContentView();
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        return intent;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initWidgets() {
        ToastUtils.showToast("sdfsdfdsf" + ChannelUtils.getChannel(getContext()));
        Log.e("", "channel initWidgets " + ChannelUtils.getChannel(getContext()));
        AndroidProcesses.isMyProcessInTheForeground();
        new CustomHandlerThread() {
            @Override
            protected void onLooperInterAsync() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected void onLooperInter() {

            }
        }.start();
        String str = "在的低剂量辐射导致罹患癌症、智力不足、神经系统疾病和遗传突变的人口逐年增加。\n" +
                "——白俄罗斯百科全书\n" +
                "一九八六年四月二十九日，波兰、德国、奥地利和罗马尼亚都检测到高剂量辐射。四月三十日，瑞士和意大利北部，五月一日、二日，法国、比利时、荷兰、英国和希腊北部，五月三日，以色列、科威特和土耳其，也陆续检测到辐射。辐射粒子飘散到全球：五月二日，日本，五月五日，印度，五月五日、六日，美国和加拿大，都陆续检测到辐射。不到一个星期，切尔诺贝利就成为全世界的问题。\n" +
                "——《切尔诺贝利灾变的影响》，明斯克，萨哈罗夫国际辐射生态学学院\n" +
                "目前用石棺封住的四号反应炉炉心，仍有大约二十吨核燃料，没有人知道里面的情况究竟如何...\n" +
                "我们是空气，我们不是土地……\n" +
                "——马马达舒维利\n" +
                "我不知道该说什么，关于死亡还是爱情？也许两者是一样的，我该讲哪一种？\n" +
                "我们才刚结婚，连到商店买东西都还会牵手。我告诉他：“我爱你。”但当时我不知道自己有多爱他，我不知道……我们住在消防局的二楼宿舍，和三对年轻夫妇共享一间厨房，红色的消防车就停在一楼。那是他的工作，我向来知道他发生了什么事——他人在哪里，他好不好。\n" +
                "那天晚上我听到声响，探头望向窗外。他看到我就说：“把窗户关上，回去睡觉。反应炉失火了，我马上回来。”\n" +
                "我没有亲眼看到爆炸，只看到火焰。所有东西都在发亮。火光冲天，烟雾弥漫，热气逼人。他一直没回来。\n" +
                "屋顶的沥青燃烧，产生烟雾。他后来说，感...\n" +
                "可是我怎么能离开他？他说：“快走！离开这里！你要保护宝宝。”\n" +
                "“我先帮你买牛奶，再决定怎么做。”\n" +
                "这时我的朋友唐雅·克比诺克和她爸爸跑了进来，她的丈夫也在同一间病房。我们跳上她爸爸的车，开到大约三公里外的镇上，买了六瓶三升的牛奶给大家喝。但是他们喝了之后就开始呕吐，频频失去知觉。医生只好帮他们打点滴。医生说他们是瓦斯中毒，没人提到和辐射有关的事。\n" +
                "没多久，整座城市就被军车淹没，所有道路封闭，电车火车停驶，军人用白色粉末清洗街道。我很担心第二天怎么出城买新鲜牛奶。没人提到辐射的事，只有军人戴着口罩。城里人依旧到店里买面包，提着袋口敞开的面包在街上走，还有人吃放在盘子上的纸杯蛋糕。\n" +
                "那天晚上我进不...";
        mText.setText(str);
    }

    @Override
    protected void initWidgetsActions() {
        mTestText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("sdfdsfdsfsdfdsf");
            }
        });
        mTestText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ToastUtils.showToast("sdfdsfdsfsdfdsf");
                return false;
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @NonNull
    @Override
    protected HomePresenter createPresenter(Context context) {
        return new HomePresenter(context);
    }
}
