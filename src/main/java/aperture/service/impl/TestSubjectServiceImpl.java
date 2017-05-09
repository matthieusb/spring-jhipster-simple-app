package aperture.service.impl;

import aperture.model.TestSubject;
import aperture.repository.RoomRepository;
import aperture.service.TestSubjectService;
import org.springframework.stereotype.Service;

@Service
public class TestSubjectServiceImpl implements TestSubjectService {

    private final RoomRepository roomRepository;


    public TestSubjectServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public boolean allRoomsProvidedExist(TestSubject testSubject) {
        return testSubject.getRooms().stream()
            .allMatch(subjectRoom ->
                roomRepository.findById(subjectRoom.getId()) != null
                    && !roomRepository.findByName(subjectRoom.getName()).isEmpty()
                    && roomRepository.findByNumber(subjectRoom.getNumber()) != null
            );
    }

}
