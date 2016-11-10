package com.wencharm.fun.presentation.profile;

import android.content.Context;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.util.AttributeSet;

/**
 * Created by Wencharm on 07/11/2016.
 */

public class ProfileTransiton extends TransitionSet {

	public ProfileTransiton() {
		init();
	}

	/**
	 * This constructor allows us to use this transition in XML
	 */
	public ProfileTransiton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setOrdering(ORDERING_TOGETHER);
		addTransition(new ChangeBounds()).addTransition(new ChangeTransform());
	}
}
