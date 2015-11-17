package com.wecanstudio.xdsjs.save.View.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.wecanstudio.xdsjs.save.MyApplication;
import com.wecanstudio.xdsjs.save.ViewModel.ViewModel;


/**
 * Base {@link Activity} class for every Activity in this application.
 */
public abstract class BaseActivity<VM extends ViewModel, B extends ViewDataBinding> extends Activity {

  private VM viewModel;
  private B binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onStart() {
    super.onStart();
  }

  @Override
  protected void onResume() {
    super.onResume();
    MyApplication.getInstance().setCurrentActivity(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    clearReferences();
  }

  @Override
  protected void onStop() {
    super.onStop();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    clearReferences();
  }

  @Override
  protected void onRestart() {
    super.onRestart();
  }

  /**
   * Adds a {@link Fragment} to this activity's layout.
   *
   * @param containerViewId The container view to where add the fragment.
   * @param fragment The fragment to be added.
   */
  protected void addFragment(int containerViewId, Fragment fragment, String tag) {
    FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment, tag);
    fragmentTransaction.commit();
  }

  public <T extends Fragment>T getFragment(String tag) {
    return (T) getFragmentManager().findFragmentByTag(tag);
  }

  public void setViewModel(@NonNull VM viewModel) {
    this.viewModel = viewModel;
  }

  public VM getViewModel() {
    if (viewModel == null) {
      throw new NullPointerException("You should setViewModel first!");
    }
    return viewModel;
  }

  public void setBinding(@NonNull B binding) {
    this.binding = binding;
  }

  public B getBinding() {
    if (binding == null) {
      throw new NullPointerException("You should setBinding first!");
    }
    return binding;
  }


  private void clearReferences(){
    Activity currActivity = MyApplication.getInstance().getCurrentActivity();
    if (this.equals(currActivity))
      MyApplication.getInstance().setCurrentActivity(null);
  }
}
