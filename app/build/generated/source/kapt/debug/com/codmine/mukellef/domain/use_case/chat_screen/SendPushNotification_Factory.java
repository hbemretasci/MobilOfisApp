// Generated by Dagger (https://dagger.dev).
package com.codmine.mukellef.domain.use_case.chat_screen;

import com.codmine.mukellef.domain.repository.OnesignalRepository;
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
public final class SendPushNotification_Factory implements Factory<SendPushNotification> {
  private final Provider<OnesignalRepository> oneSignalProvider;

  public SendPushNotification_Factory(Provider<OnesignalRepository> oneSignalProvider) {
    this.oneSignalProvider = oneSignalProvider;
  }

  @Override
  public SendPushNotification get() {
    return newInstance(oneSignalProvider.get());
  }

  public static SendPushNotification_Factory create(
      Provider<OnesignalRepository> oneSignalProvider) {
    return new SendPushNotification_Factory(oneSignalProvider);
  }

  public static SendPushNotification newInstance(OnesignalRepository oneSignal) {
    return new SendPushNotification(oneSignal);
  }
}
