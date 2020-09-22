package com.appinnovates.campeat.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.CharityModelResult;

import java.util.List;


public class MultiSelectedAdapter extends BaseAdapter {
    private List<CharityModelResult> commonList;
    private final MultiSelectedAdapter myAdapter;
    private ActionListener actionListener;
    public MultiSelectedAdapter(List<CharityModelResult> commonList) {
        this.commonList = commonList;
        myAdapter = this;
    }

    static class ViewHolder {
        private TextView textViewName;
        private ImageView imageView;
        private RelativeLayout relativeLayoutItem;
        private LinearLayout linearLayoutItem;
        private LinearLayout linearLayoutButtons;
        private Button cancelButton;
        private Button okButton;
    }

    @Override
    public int getCount() {
        return commonList.size();
    }

    @Override
    public Object getItem(int i) {
        return commonList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) viewGroup.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        convertView = layoutInflater.inflate(R.layout.spinner_charity_view, null);
        TextView textViewName = (TextView) convertView.findViewById(R.id.text_view_id);
        StringBuilder stringBuilder = new StringBuilder();
        Boolean selected = false;
        if (commonList.get(0).getSelected()) {
            textViewName.setText(commonList.get(0).getCharity().getCharityName());
            selected = true;
        } else {
            boolean firstItem = true;
            for (int i = 1; i < commonList.size() - 1; i++) {
                if (commonList.get(i).getSelected()) {
                    selected = true;
                    if (!firstItem) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(commonList.get(i).getCharity().getCharityName());
                    firstItem = false;
                }
            }

            String name = stringBuilder.toString();
            textViewName.setText(name);
        }

        if (!selected) {
            textViewName.setText(convertView.getResources().getString(R.string.choose_charity));
        }
        return convertView;
    }

    @Override
    public View getDropDownView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_spinner_multiselection, null);
            viewHolder = new ViewHolder();
            viewHolder.textViewName = (TextView) convertView.findViewById(R.id.text_view_id);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_view_select);
            viewHolder.relativeLayoutItem = convertView.findViewById(R.id.relative_layout_item);
            viewHolder.linearLayoutItem = convertView.findViewById(R.id.linear_layout_item);
            viewHolder.linearLayoutButtons = convertView.findViewById(R.id.linear_layout_buttons);
            viewHolder.cancelButton = convertView.findViewById(R.id.cancel_button);
            viewHolder.okButton = convertView.findViewById(R.id.ok_button);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == commonList.size()-1 && commonList.get(position) == null) {
            viewHolder.linearLayoutItem.setVisibility(View.GONE);
            viewHolder.linearLayoutButtons.setVisibility(View.VISIBLE);
        } else {
            viewHolder.linearLayoutItem.setVisibility(View.VISIBLE);
            viewHolder.linearLayoutButtons.setVisibility(View.GONE);
            viewHolder.textViewName.setText(commonList.get(position).getCharity().getCharityName());
            if (commonList.get(position).getSelected()) {
                viewHolder.imageView.setImageDrawable(convertView.getResources()
                        .getDrawable(R.drawable.selected_box));
            } else {
                viewHolder.imageView.setImageDrawable(convertView.getResources()
                        .getDrawable(R.drawable.unselected_box));
            }
            viewHolder.relativeLayoutItem.setOnClickListener(view -> {
                if (position == commonList.size()-1)
                    return;
                if (position == 0) {            //UnSelect All
                    if (commonList.get(position).getSelected()) {
                        for (int i = 0; i < commonList.size()-1; i++) {
                            commonList.get(i).setSelected(false);
                        }
                    } else {                    //Select All
                        for (int i = 0; i < commonList.size()-1; i++) {
                            commonList.get(i).setSelected(true);
                        }
                    }
                } else {                        //Select Single
                    if (commonList.get(position).getSelected()) {
                        commonList.get(position).setSelected(false);
                    } else {
                        commonList.get(position).setSelected(true);
                    }
                    commonList.get(0).setSelected(false);
                }

                myAdapter.notifyDataSetChanged();
            });
        }
        viewHolder.cancelButton.setOnClickListener(v -> {
            View root = parent.getRootView();
            root.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
            root.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
            myAdapter.notifyDataSetChanged();
        });

        viewHolder.okButton.setOnClickListener(v -> {
            actionListener.onOkClick();
            View root = parent.getRootView();
            root.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
            root.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
            myAdapter.notifyDataSetChanged();
        });

        return convertView;
    }

    public void setActionListener(ActionListener listener) {
        actionListener = listener;
    }

    public interface ActionListener {
        void onOkClick();
    }
}
