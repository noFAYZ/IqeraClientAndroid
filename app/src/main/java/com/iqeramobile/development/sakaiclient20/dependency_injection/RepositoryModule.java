package com.iqeramobile.development.sakaiclient20.dependency_injection;

import com.iqeramobile.development.sakaiclient20.networking.services.AnnouncementsService;
import com.iqeramobile.development.sakaiclient20.networking.services.AssignmentsService;
import com.iqeramobile.development.sakaiclient20.networking.services.CoursesService;
import com.iqeramobile.development.sakaiclient20.networking.services.GradeService;
import com.iqeramobile.development.sakaiclient20.networking.services.ResourcesService;
import com.iqeramobile.development.sakaiclient20.persistence.access.AnnouncementDao;
import com.iqeramobile.development.sakaiclient20.persistence.access.AssignmentDao;
import com.iqeramobile.development.sakaiclient20.persistence.access.AttachmentDao;
import com.iqeramobile.development.sakaiclient20.persistence.access.CourseDao;
import com.iqeramobile.development.sakaiclient20.persistence.access.GradeDao;
import com.iqeramobile.development.sakaiclient20.persistence.access.ResourceDao;
import com.iqeramobile.development.sakaiclient20.persistence.access.SitePageDao;
import com.iqeramobile.development.sakaiclient20.repositories.AnnouncementRepository;
import com.iqeramobile.development.sakaiclient20.repositories.AssignmentRepository;
import com.iqeramobile.development.sakaiclient20.repositories.CourseRepository;
import com.iqeramobile.development.sakaiclient20.repositories.GradeRepository;
import com.iqeramobile.development.sakaiclient20.repositories.ResourceRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {DaoModule.class, ServiceModule.class})
class RepositoryModule {

    @Singleton
    @Provides
    static CourseRepository provideCourseRepository(
            CourseDao courseDao,
            SitePageDao sitePageDao,
            CoursesService coursesService) {
        return new CourseRepository(courseDao, sitePageDao, coursesService);
    }

    @Singleton
    @Provides
    static ResourceRepository provideResourceRepository(
            ResourceDao dao,
            ResourcesService service) {
        return new ResourceRepository(dao, service);
    }

    @Singleton
    @Provides
    static AssignmentRepository provideAssignmentRepository(
            CourseDao courseDao,
            AssignmentDao assignmentDao,
            AttachmentDao attachmentDao,
            AssignmentsService assignmentsService
    ) {
        return new AssignmentRepository(courseDao, assignmentDao, attachmentDao, assignmentsService);
    }

    @Singleton
    @Provides
    static AnnouncementRepository provideAnnouncementRepository(
            AnnouncementDao announcementDao,
            AttachmentDao attachmentDao,
            AnnouncementsService announcementsService) {
        return new AnnouncementRepository(announcementDao, attachmentDao, announcementsService);
    }

    @Singleton
    @Provides
    static GradeRepository provideGradesRepository(
            GradeDao gradeDao,
            GradeService gradesService) {
        return new GradeRepository(gradeDao, gradesService);
    }

}
