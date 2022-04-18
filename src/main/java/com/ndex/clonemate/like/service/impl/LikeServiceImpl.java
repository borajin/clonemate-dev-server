package com.ndex.clonemate.like.service.impl;

import com.ndex.clonemate.like.domain.Like;
import com.ndex.clonemate.like.domain.LikeRepository;
import com.ndex.clonemate.like.service.LikeService;
import com.ndex.clonemate.todo.domain.Todo;
import com.ndex.clonemate.todo.domain.repository.TodoRepository;
import com.ndex.clonemate.user.domain.User;
import com.ndex.clonemate.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {
    private final static String ERROR_NO_USER = "[ERROR] 해당 사용자가 없습니다.";
    private final static String ERROR_NO_TODO = "[ERROR] 해당 투두가 없습니다.";

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    @Override
    public List<Like> getLikeToDate(LocalDate date) {
        return likeRepository.findByTodo_Date(date);
    }

    @Override
    @Transactional
    public void like(Long todoId, Long userId) {
        Todo todo = todoRepository.findById(Todo.class, todoId).orElseThrow(()-> new IllegalArgumentException(ERROR_NO_TODO));
        User user = userRepository.findById(User.class, userId).orElseThrow(()-> new IllegalArgumentException(ERROR_NO_USER));

        Like like = Like.builder().todo(todo).user(user).build();
        likeRepository.save(like);
    }

    @Override
    @Transactional
    public void unLike(Long id) {
        likeRepository.deleteById(id);
    }
}
