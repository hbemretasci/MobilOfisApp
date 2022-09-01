// Generated by Dagger (https://dagger.dev).
package com.codmine.mukellef.domain.use_case.document_screen;

import com.codmine.mukellef.domain.repository.MobileOfficeRepository;
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
public final class GetDocuments_Factory implements Factory<GetDocuments> {
  private final Provider<MobileOfficeRepository> repositoryProvider;

  public GetDocuments_Factory(Provider<MobileOfficeRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetDocuments get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetDocuments_Factory create(Provider<MobileOfficeRepository> repositoryProvider) {
    return new GetDocuments_Factory(repositoryProvider);
  }

  public static GetDocuments newInstance(MobileOfficeRepository repository) {
    return new GetDocuments(repository);
  }
}