package com.mycalculator.WiFiDataAcquisition.util;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycalculator.WiFiDataAcquisition.R;

/**
 * @author Chunray
 * 自定义ProgressDialog
 */
public class CustomProgressDialog extends Dialog implements OnCancelListener{
    @SuppressWarnings("unused")
	private Context context = null;
    private static CustomProgressDialog customProgressDialog = null;
     
    public CustomProgressDialog(Context context){
        super(context);
        this.context = context;
    }
     
    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }
     
    public static CustomProgressDialog createDialog(Context context){
    	if(customProgressDialog != null){
    		customProgressDialog.dismiss();
    	}
	 	customProgressDialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.custom_progress_dialog);
        customProgressDialog.setCanceledOnTouchOutside(false);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return customProgressDialog;
    }
  
    public void onWindowFocusChanged(boolean hasFocus){
         
        if (customProgressDialog == null){
            return;
        }
         
        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }
     
    /**
     *
     * [Summary]
     *       setMessage 提示内容
     * @param strMessage
     * @return
     *
     */
    public CustomProgressDialog setMessage(String strMessage){
        TextView tvMsg = (TextView)customProgressDialog.findViewById(R.id.id_tv_loadingmsg);
         
        if (tvMsg != null){
            tvMsg.setText(strMessage);
        }
        return customProgressDialog;
    }

	@Override
	public void onCancel(DialogInterface dialog) {
		// TODO Auto-generated method stub
		customProgressDialog.dismiss();
	}
}
