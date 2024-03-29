// Generated by Dagger (https://dagger.dev).
package com.codmine.mukellef.domain.use_case.splash_screen;

import com.codmine.mukellef.domain.repository.FirebaseRepository;
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
public final class GetUserLoginInformation_Factory implements Factory<GetUserLoginInformation> {
  private final Provider<FirebaseRepository> repositoryProvider;

  public GetUserLoginInformation_Factory(Provider<FirebaseRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetUserLoginInformation get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetUserLoginInformation_Factory create(
      Provider<FirebaseRepository> repositoryProvider) {
    return new GetUserLoginInformation_Factory(repositoryProvider);
  }

  public static GetUserLoginInformation newInstance(FirebaseRepository repository) {
    return new GetUserLoginInformation(repository);
  }
}
