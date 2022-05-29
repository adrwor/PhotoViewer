package apps.aw.simplephotos.android.ui.browser.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import apps.aw.simplephotos.R;
import apps.aw.simplephotos.java.ItemList;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> {

    private static final String TAG = "FileListAdapter";

    private ItemList itemList; //contains the filelist as well as the current selection
    private Context context;

    /**
     * Constructor.
     * @param itemList
     */
    public FileListAdapter(ItemList itemList, Context context) {
//        Log.i("FileListAdapter", "FileListAdapter()");
        this.itemList = itemList;
        this.context = context;
    }

    /**
     * Sets the file list (with selection and state).
     * @param itemList FileListSelection object
     */
    public void setFileListSelection(ItemList itemList) {
//        Log.i("FileListAdapter", "setFileListSelection() length: " + fileList.getListSize());
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public void setFocusedItem(int index) {
        itemList.setFocus(index);
        notifyItemChanged(index);
    }

    /**
     * Sets the selection.
     * @param selection FileListSelection.Selection object
     */
    public void setSelection(int selection) {
        int oldSelectionIndex = itemList.getFocus(); //store old selection position
        itemList.setFocus(selection);   //update selection position
        notifyItemChanged(oldSelectionIndex);    //update view at old position
        notifyItemChanged(itemList.getFocus()); //update view at new position
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("FileListAdapter", "onCreateViewHolder()");
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_view, parent, false);
        return new ViewHolder(v);   //return a new viewholder
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i("FileListAdapter", "onBindViewHolder()");
        //holder.icon.setImageDrawable(null);   //TODO: set drawable according to file type
        int p = holder.getAdapterPosition();
        holder.text.setText(itemList.getFileList().get(p)); //set text
        if(position == itemList.getFocus()) {
            holder.mView.setBackgroundResource(R.color.colorItemFocused);
            holder.text.setTextColor(context.getResources().getColor(R.color.colorTextItemFocused));
        } else {
            holder.mView.setBackgroundResource(R.color.colorItemDefault);
            holder.text.setTextColor(context.getResources().getColor(R.color.colorTextItemDefault));
        }
    }

    @Override
    public int getItemCount() {
//        Log.i("FileListAdapter", "getItemCount()");
        return itemList.getListSize();
    }

    /**
     * ViewHolder
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView text;
        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.i("FileListAdapter", "ViewHolder()");
            icon = (ImageView) itemView.findViewById(R.id.imageView2);
            text = (TextView) itemView.findViewById(R.id.textView4);
            mView = itemView;
        }
    }

}
