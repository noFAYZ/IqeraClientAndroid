package com.iqeramobile.development.sakaiclient20.dependency_injection;

import com.iqeramobile.development.sakaiclient20.networking.services.AnnouncementsService;
import com.iqeramobile.development.sakaiclient20.networking.services.AssignmentsService;
import com.iqeramobile.development.sakaiclient20.networking.services.ChatService;
import com.iqeramobile.development.sakaiclient20.networking.services.CoursesService;
import com.iqeramobile.development.sakaiclient20.networking.services.GradeService;
import com.iqeramobile.development.sakaiclient20.networking.services.ResourcesService;
import com.iqeramobile.development.sakaiclient20.networking.services.SessionService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = NetworkingModule.class)
class ServiceModule {

    @Singleton
    @Provides
    static SessionService provideUserService(@Named("default_retrofit") Retrofit retrofit) {
        return retrofit.create(SessionService.class);
    }

    @Singleton
    @Provides
    static ResourcesService provideResourcesService(@Named("resource_retrofit") Retrofit retrofit) {
        return retrofit.create(ResourcesService.class);
    }

    @Singleton
    @Provides
    static CoursesService provideCoursesService(@Named("course_retrofit") Retrofit retrofit) {
        return retrofit.create(CoursesService.class);
    }

    @Singleton
    @Provides
    static AssignmentsService provideAssignmentsService(@Named("assignment_retrofit") Retrofit retrofit) {
        return retrofit.create(AssignmentsService.class);
    }

    @Singleton
    @Provides
    static AnnouncementsService provideAnnouncementsService(@Named("announcement_retrofit") Retrofit retrofit) {
        return retrofit.create(AnnouncementsService.class);
    }

    @Singleton
    @Provides
    static GradeService provideGradesService(@Named("grades_retrofit") Retrofit retrofit) {
        return retrofit.create(GradeService.class);
    }

    @Provides
    static ChatService provideChatService(@Named("default_retrofit") Retrofit retrofit) {
        return retrofit.create(ChatService.class);
    }

}
