package com.zdu.simplenewsdemo.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.zdu.simplenewsdemo.R;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;

/**
 * 聊天界面
 */
public class ConversationActivity extends AppCompatActivity {
    @Bind(R.id.header_toolbar)
    Toolbar mConversationToolbar;
    @Bind(R.id.header_title)
    TextView mConversationTitle;
    private String mTargetId;
    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        mConversationToolbar.setTitle("");
        setSupportActionBar(mConversationToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//toolbar上向左的小箭头可以点击
        mConversationToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTargetId = getIntent().getData().getQueryParameter("targetId");
        mConversationTitle.setText(mTargetId);
        mConversationType = Conversation.ConversationType.valueOf(getIntent().getData().getLastPathSegment().toUpperCase(Locale.getDefault()));
        enterFragment(mConversationType, mTargetId);
    }

    private void enterFragment(Conversation.ConversationType conversationType, String targetId) {
        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(conversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", targetId).build();

        fragment.setUri(uri);
    }
}
