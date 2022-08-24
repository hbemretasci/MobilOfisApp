// Generated by Dagger (https://dagger.dev).
package com.codmine.mukellef.presentation.chat_screen.messages;

import androidx.lifecycle.SavedStateHandle;
import com.codmine.mukellef.domain.use_case.chat_screen.AddListener;
import com.codmine.mukellef.domain.use_case.chat_screen.PostMessage;
import com.codmine.mukellef.domain.use_case.chat_screen.RemoveListener;
import com.codmine.mukellef.domain.use_case.splash_screen.GetUserLoginData;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MessagesViewModel_Factory implements Factory<MessagesViewModel> {
  private final Provider<AddListener> addListenerProvider;

  private final Provider<RemoveListener> removeListenerProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<GetUserLoginData> getUserLoginDataProvider;

  private final Provider<PostMessage> postMessageProvider;

  public MessagesViewModel_Factory(Provider<AddListener> addListenerProvider,
      Provider<RemoveListener> removeListenerProvider,
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<GetUserLoginData> getUserLoginDataProvider,
      Provider<PostMessage> postMessageProvider) {
    this.addListenerProvider = addListenerProvider;
    this.removeListenerProvider = removeListenerProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.getUserLoginDataProvider = getUserLoginDataProvider;
    this.postMessageProvider = postMessageProvider;
  }

  @Override
  public MessagesViewModel get() {
    return newInstance(addListenerProvider.get(), removeListenerProvider.get(), savedStateHandleProvider.get(), getUserLoginDataProvider.get(), postMessageProvider.get());
  }

  public static MessagesViewModel_Factory create(Provider<AddListener> addListenerProvider,
      Provider<RemoveListener> removeListenerProvider,
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<GetUserLoginData> getUserLoginDataProvider,
      Provider<PostMessage> postMessageProvider) {
    return new MessagesViewModel_Factory(addListenerProvider, removeListenerProvider, savedStateHandleProvider, getUserLoginDataProvider, postMessageProvider);
  }

  public static MessagesViewModel newInstance(AddListener addListener,
      RemoveListener removeListener, SavedStateHandle savedStateHandle,
      GetUserLoginData getUserLoginData, PostMessage postMessage) {
    return new MessagesViewModel(addListener, removeListener, savedStateHandle, getUserLoginData, postMessage);
  }
}
