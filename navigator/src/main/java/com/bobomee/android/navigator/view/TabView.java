package com.bobomee.android.navigator.view;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import com.bobomee.android.navigator.interfaces.ITabView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016/10/24.上午9:53.
 *
 * @author bobomee.
 * @description
 */

public class TabView extends RelativeLayout implements ITabView {

  private List<OnCheckedChangeListener> mOnCheckedChangeListeners;
  private ITabView.OnCheckedChangeListener mOnCheckedChangeWidgetListener;

  private boolean mBroadcasting;//是否调用checkedListener中
  private boolean mChecked;//是否checked状态
  private boolean shouldKeep;//是否保持check状态

  public void setShouldKeep(boolean _shouldKeep) {
    shouldKeep = _shouldKeep;
  }

  private GestureDetector gestureDetector;

  public TabView(Context context) {
    this(context, null);
  }

  public TabView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setGravity(Gravity.CENTER);
    initTouchEvent();
  }

  /**
   * <p>Changes the checked state of this button.</p>
   *
   * @param checked true to check the button, false to uncheck it
   */
  @Override public void setChecked(boolean checked) {
    if (mChecked != checked) {
      mChecked = checked;

      setSelected(checked);

      refreshDrawableState();

      // Avoid infinite recursions if setChecked() is called from a listener
      if (mBroadcasting) {
        return;
      }

      mBroadcasting = true;

      if (null != mOnCheckedChangeListeners) {
        for (int i = 0, z = mOnCheckedChangeListeners.size(); i < z; i++) {
          ITabView.OnCheckedChangeListener listener = mOnCheckedChangeListeners.get(i);
          if (null != listener) {
            listener.onCheckedChanged(this, mChecked);
          }
        }
      }//可以用于提示上一个tab 改变状态 -->
      if (mOnCheckedChangeWidgetListener != null) {
        mOnCheckedChangeWidgetListener.onCheckedChanged(this, mChecked);
      }

      mBroadcasting = false;
    }
  }

  @Override public boolean isChecked() {
    return mChecked;
  }

  /**
   * {@inheritDoc}
   * <p>
   * If the tab view is already checked, this method will not toggle the radio button.
   */
  @Override public void toggle() {
    // we override to prevent toggle when the radio is already
    // checked (as opposed to check boxes widgets)
    if (!shouldKeep || !isChecked()) setChecked(!mChecked);
  }

  ///////////////////////////////////////touchevent///////////////
  @Override public boolean performClick() {
    toggle();
    return super.performClick();
  }

  private void initTouchEvent() {
    gestureDetector =
        new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
          @Override public boolean onSingleTapConfirmed(MotionEvent e) {
            return true;
          }

          @Override public boolean onSingleTapUp(MotionEvent e) {
            return true;
          }
        });
    this.setClickable(true);
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    gestureDetector.onTouchEvent(event);
    return super.onTouchEvent(event);
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent event) {
    this.onTouchEvent(event);
    return super.onInterceptTouchEvent(event);
  }
  ////////////////////////////////////listener/////////////////

  /**
   * Register a callback to be invoked when the checked state of this button
   * changes.
   *
   * @param listener the callback to call on checked state change
   */
  public void addOnCheckedChangeListener(ITabView.OnCheckedChangeListener listener) {
    if (mOnCheckedChangeListeners == null) {
      mOnCheckedChangeListeners = new ArrayList<>();
    }
    mOnCheckedChangeListeners.add(listener);
  }

  /**
   * Register a callback to be invoked when the checked state of this button
   * changes. This callback is used for internal purpose only.
   *
   * @param listener the callback to call on checked state change
   * @hide
   */
  public void setOnCheckedChangeWidgetListener(ITabView.OnCheckedChangeListener listener) {
    mOnCheckedChangeWidgetListener = listener;
  }

  ///////////////////////////////////state////////////
  @Override public Parcelable onSaveInstanceState() {
    Parcelable superState = super.onSaveInstanceState();

    SavedState ss = new SavedState(superState);

    ss.checked = isChecked();
    return ss;
  }

  @Override public void onRestoreInstanceState(Parcelable state) {
    SavedState ss = (SavedState) state;

    super.onRestoreInstanceState(ss.getSuperState());
    setChecked(ss.checked);
    requestLayout();
  }

  static class SavedState extends BaseSavedState {
    public static final Creator<SavedState> CREATOR =
        new Creator<SavedState>() {
          public SavedState createFromParcel(Parcel in) {
            return new SavedState(in);
          }

          public SavedState[] newArray(int size) {
            return new SavedState[size];
          }
        };
    boolean checked;

    /**
     * Constructor called from {@link TabView#onSaveInstanceState()}
     */
    SavedState(Parcelable superState) {
      super(superState);
    }

    /**
     * Constructor called from {@link #CREATOR}
     */
    private SavedState(Parcel in) {
      super(in);
      checked = (Boolean) in.readValue(null);
    }

    @Override public void writeToParcel(Parcel out, int flags) {
      super.writeToParcel(out, flags);
      out.writeValue(checked);
    }

    @Override public String toString() {
      return "TabView.SavedState{"
          + Integer.toHexString(System.identityHashCode(this))
          + " checked="
          + checked
          + "}";
    }
  }

  public void setContentView(View _contentView) {
    removeAllViews();
    addView(_contentView);
  }
}
