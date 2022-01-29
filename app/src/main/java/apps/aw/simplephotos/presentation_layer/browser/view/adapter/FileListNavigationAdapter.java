package apps.aw.simplephotos.presentation_layer.browser.view.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import apps.aw.simplephotos.R;
import apps.aw.simplephotos.presentation_layer.browser.utils.FileList;

public class FileListNavigationAdapter extends RecyclerView.Adapter<FileListNavigationAdapter.ViewHolder> {

    private FileList fileList; //contains the filelist as well as the current selection
    private ItemFocusedListener itemFocusedListener;    //handles focus events
    private ItemClickListener itemClickListener;        //handles the click events

    /**
     * Constructor.
     * @param fileList
     */
    public FileListNavigationAdapter(FileList fileList, ItemFocusedListener itemFocusedListener, ItemClickListener itemClickListener) {
        this.fileList = fileList;
        this.itemFocusedListener = itemFocusedListener;
        this.itemClickListener = itemClickListener;
    }

    /**
     * Sets the file list (with selection and state).
     * @param fileList FileListSelection object
     */
    public void setFileListSelection(FileList fileList) {
        Log.i("FileListAdapter", "setFileListSelection() length: " + fileList.getListSize());
        this.fileList = fileList;
        notifyDataSetChanged();
    }

    /**
     * Sets the selection.
     * @param selection FileListSelection.Selection object
     */
    public void setSelection(int selection) {
        int oldSelectionIndex = fileList.getSelection(); //store old selection position
        fileList.setSelection(selection);   //update selection position
        notifyItemChanged(oldSelectionIndex);    //update view at old position
        notifyItemChanged(fileList.getSelection()); //update view at new position
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("FileListAdapter", "onCreateViewHolder()");
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_view, parent, false);
        v.setFocusable(true);
        return new ViewHolder(v);   //return a new viewholder
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i("FileListAdapter", "onBindViewHolder()");
        //holder.icon.setImageDrawable(null);   //TODO: set drawable according to file type
        int p = holder.getAdapterPosition();
        holder.text.setText(fileList.getFileList().get(p)); //set text
        holder.view.setOnClickListener(view -> itemClickListener.onItemClick(p));
        holder.view.setOnFocusChangeListener(
                (v, hasFocus) ->
                {itemFocusedListener.onItemFocusChanged(p, hasFocus);

                }
        );
    }

    @Override
    public int getItemCount() {
//        Log.i("FileListAdapter", "getItemCount()");
        return fileList.getListSize();
    }




    /**
     * ViewHolder
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView text;
        public View view;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.i("FileListAdapter", "ViewHolder()");
            icon = (ImageView) itemView.findViewById(R.id.imageView2);
            text = (TextView) itemView.findViewById(R.id.textView4);
            view = itemView;
        }
    }


    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public interface ItemFocusedListener {
        void onItemFocusChanged(int position, boolean hasFocus);
    }

}
