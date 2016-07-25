// Generated code from Butter Knife. Do not modify!
package com.schoolshieldchild.view.activity;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import com.schoolshieldchild.R;
import com.schoolshieldchild.view.custom_controls.Button_Regular;
import com.schoolshieldchild.view.custom_controls.EditText_Regular;
import com.schoolshieldchild.view.custom_controls.TextView_Regular;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class LoginActivity_ViewBinding<T extends LoginActivity> implements Unbinder {
  protected T target;

  private View view2131624061;

  public LoginActivity_ViewBinding(final T target, Finder finder, Object source) {
    this.target = target;

    View view;
    view = finder.findRequiredView(source, R.id.button_confirm, "field 'button_confirm' and method 'setConfirmClick'");
    target.button_confirm = finder.castView(view, R.id.button_confirm, "field 'button_confirm'", Button_Regular.class);
    view2131624061 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.setConfirmClick();
      }
    });
    target.editText_childpin = finder.findRequiredViewAsType(source, R.id.editText_childpin, "field 'editText_childpin'", EditText_Regular.class);
    target.textView_HowToGetPin = finder.findRequiredViewAsType(source, R.id.textView_HowToGetPin, "field 'textView_HowToGetPin'", TextView_Regular.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.button_confirm = null;
    target.editText_childpin = null;
    target.textView_HowToGetPin = null;

    view2131624061.setOnClickListener(null);
    view2131624061 = null;

    this.target = null;
  }
}
