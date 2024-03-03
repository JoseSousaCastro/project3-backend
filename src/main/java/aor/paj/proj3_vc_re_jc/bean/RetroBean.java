package aor.paj.proj3_vc_re_jc.bean;

import aor.paj.proj3_vc_re_jc.dao.RetroEventDao;
import aor.paj.proj3_vc_re_jc.dao.UserDao;
import aor.paj.proj3_vc_re_jc.dto.AddCommentDto;
import aor.paj.proj3_vc_re_jc.dto.CreateRetroEventDto;
import aor.paj.proj3_vc_re_jc.dto.RetroCommentDto;
import aor.paj.proj3_vc_re_jc.entity.RetroCommentEntity;
import aor.paj.proj3_vc_re_jc.entity.RetroEventEntity;
import aor.paj.proj3_vc_re_jc.entity.UserEntity;
import aor.paj.proj3_vc_re_jc.enums.RetroCommentCategory;
import jakarta.ejb.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class RetroBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    RetroEventDao retroEventDao;
    @EJB
    UserDao userDao;

    public RetroBean() {
    }

    public boolean addRetrospective(String token, CreateRetroEventDto createRetroEventDTO) {
        UserEntity userEntity = userDao.findUserByToken(token);
        boolean added = true;
        if (userEntity != null) {
            if (createRetroEventDTO.getTitle().isBlank() || createRetroEventDTO.getSchedulingDate() == null) {
                System.out.println("Title or date is blank" + createRetroEventDTO.getTitle() + createRetroEventDTO.getSchedulingDate());
                added = false;
            } else {
                System.out.println("entraste aqui?" + createRetroEventDTO.getTitle() + createRetroEventDTO.getSchedulingDate());
                LocalDate date = LocalDate.parse(createRetroEventDTO.getSchedulingDate());
                RetroEventEntity retroEventEntity = new RetroEventEntity();
                retroEventEntity.setTitle(createRetroEventDTO.getTitle());
                retroEventEntity.setSchedulingDate(date);
                retroEventDao.persist(retroEventEntity);
            }
        }
        return added;
    }

    public List<CreateRetroEventDto> getRetrospectives() {
        List<RetroEventEntity> retroEventEntities = retroEventDao.getAllRetroEvents();

        List<CreateRetroEventDto> retroEventDTOs = new ArrayList<>();
        for (RetroEventEntity retroEventEntity : retroEventEntities) {
            CreateRetroEventDto retroEventDTO = new CreateRetroEventDto();
            retroEventDTO.setEventId(retroEventEntity.getEventId());
            retroEventDTO.setTitle(retroEventEntity.getTitle());
            retroEventDTO.setSchedulingDate(retroEventEntity.getSchedulingDate().toString());
            retroEventDTOs.add(retroEventDTO);

        }

        return retroEventDTOs;
    }

    // Método auxiliar para converter uma entidade para um DTO
    private CreateRetroEventDto convertToDTO(RetroEventEntity retroEventEntity) {

        CreateRetroEventDto retroEventDTO = new CreateRetroEventDto();
        retroEventDTO.setTitle(retroEventEntity.getTitle());
        retroEventDTO.setSchedulingDate(retroEventEntity.getSchedulingDate().toString());

        return retroEventDTO;
    }


    public AddCommentDto convertToDTO(RetroCommentEntity retroCommentEntity) {
        AddCommentDto addCommentDTO = new AddCommentDto();
        addCommentDTO.setComment(retroCommentEntity.getComment());
        addCommentDTO.setUserId(retroCommentEntity.getUser().getId());
        addCommentDTO.setEventId(retroCommentEntity.getEvent().getEventId());
        addCommentDTO.setCategory(retroCommentEntity.getCategory());
        return addCommentDTO;
    }

    public List<AddCommentDto> getComments(int id) {
        RetroEventEntity retroEventEntity = retroEventDao.find(id);
        List<AddCommentDto> addCommentDtos = new ArrayList<>();
        if (retroEventEntity != null) {
            addCommentDtos = retroEventEntity.getComments().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return addCommentDtos;
    }

    public List<String> getMembers(int id) {
        RetroEventEntity retroEventEntity = retroEventDao.find(id);
        List<String> members = new ArrayList<>();
        if (retroEventEntity != null) {
            members = retroEventEntity.getMembers().stream()
                    .map(UserEntity::getUsername)
                    .collect(Collectors.toList());
        }
        return members;
    }

    public CreateRetroEventDto getRetrospective(int id) {
        RetroEventEntity retroEventEntity = retroEventDao.find(id);
        CreateRetroEventDto createRetroEventDTO = null;
        if (retroEventEntity != null) {
            createRetroEventDTO = convertToDTO(retroEventEntity);
        }
        return createRetroEventDTO;
    }

    public AddCommentDto getComment(int id, int id2) {
        RetroEventEntity retroEventEntity = retroEventDao.find(id);
        AddCommentDto addCommentDTO = null;
        if (retroEventEntity != null) {
            addCommentDTO = convertToDTO(retroEventEntity.getComment(id2));
        }
        return addCommentDTO;
    }

    public String getMember(int id, int id2) {
        RetroEventEntity retroEventEntity = retroEventDao.find(id);
        String member = null;
        if (retroEventEntity != null) {
            member = retroEventEntity.getMembers().get(id2).getUsername();
        }
        return member;
    }

    public boolean addCommentToRetrospective(String token, int id, AddCommentDto temporaryRetroComment) {
        UserEntity userEntity = userDao.findUserByToken(token);
        boolean added = true;
        if (userEntity != null) {
            if (temporaryRetroComment.getComment().isBlank() || temporaryRetroComment.getCategory() == null) {
                added = false;
            } else {
                RetroEventEntity retroEventEntity = retroEventDao.find(id);
                if (retroEventEntity != null) {
                    RetroCommentEntity retroCommentEntity = new RetroCommentEntity();
                    retroCommentEntity.setComment(temporaryRetroComment.getComment());
                    // Convertendo a String para int
                    try {
                        int categoryValue = Integer.parseInt(temporaryRetroComment.getCategory());
                        retroCommentEntity.setCategory(RetroCommentCategory.fromValue(categoryValue));
                    } catch (NumberFormatException e) {
                        // Lidar com a exceção, a String não é um número válido
                        added = false;
                    }
                    retroCommentEntity.setUser(userEntity);
                    retroCommentEntity.setEvent(retroEventEntity);
                    retroEventEntity.addComment(retroCommentEntity);

                    retroEventDao.merge(retroEventEntity);
                } else {
                    added = false;
                }
            }
        }
        return added;
    }


    public boolean addMemberToRetrospective(int id, int id2) {
        RetroEventEntity retroEventEntity = retroEventDao.find(id);
        UserEntity userEntity = userDao.find(id2);
        boolean added = true;
        if (retroEventEntity != null && userEntity != null) {
            retroEventEntity.addMember(userEntity);
            retroEventDao.merge(retroEventEntity);
        } else {
            added = false;
        }
        return added;
    }

    public boolean editComment(int id, int id2, AddCommentDto temporaryRetroComment) {
        RetroEventEntity retroEventEntity = retroEventDao.find(id);
        boolean edited = true;
        if (retroEventEntity != null) {
            RetroCommentEntity retroCommentEntity = retroEventEntity.getComment(id2);
            if (retroCommentEntity != null) {
                retroCommentEntity.setComment(temporaryRetroComment.getComment());
                // Convertendo a String para int
                try {
                    int categoryValue = Integer.parseInt(temporaryRetroComment.getCategory());
                    retroCommentEntity.setCategory(RetroCommentCategory.fromValue(categoryValue));
                } catch (NumberFormatException e) {
                    // Lidar com a exceção, a String não é um número válido
                    System.out.println("ERRO3");
                    edited = false;
                }
                retroEventDao.merge(retroEventEntity);
            } else {
                System.out.println("ERRO1");
                edited = false;
            }
        } else {
            System.out.println("ERRO2");
            edited = false;
        }
        return edited;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean deleteComment(int id, int id2) {
        RetroEventEntity retroEventEntity = retroEventDao.find(id);
        boolean deleted = true;
        if (retroEventEntity != null) {
            RetroCommentEntity retroCommentEntity = retroEventEntity.getComment(id2);
            if (retroCommentEntity != null) {
                retroEventEntity.removeComment(retroCommentEntity);
                retroEventDao.merge(retroEventEntity);
                retroEventDao.flush();
                System.out.println("DELETED");
            } else {
                deleted = false;
            }
        } else {
            deleted = false;
        }
        return deleted;
    }

    public boolean deleteAllComments(int id) {
        RetroEventEntity retroEventEntity = retroEventDao.find(id);
        boolean deleted = true;
        if (retroEventEntity != null) {
            retroEventEntity.getComments().clear();
            retroEventDao.merge(retroEventEntity);
        } else {
            deleted = false;
        }
        return deleted;
    }

    public  List<RetroCommentDto> getCommentss (int id) {
        RetroEventEntity entity = retroEventDao.find(id);
        List<RetroCommentDto> dtos = new ArrayList<>();
        for (RetroCommentEntity en : entity.getComments()) {
            RetroCommentDto  dto = new RetroCommentDto();
            dto.setComment(en.getComment());
            dto.setCommentId(en.getCommentId());
            dto.setUserId(en.getUser().getId());


            dtos.add(dto);
        }
        return dtos;
    }

}