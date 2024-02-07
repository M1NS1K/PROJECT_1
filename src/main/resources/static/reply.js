// reply.js
document.addEventListener('DOMContentLoaded', function() {
    var replyForm = document.getElementById('reply-form');
    if (replyForm) {
        replyForm.addEventListener('submit', function(event) {
            event.preventDefault(); // 폼 기본 제출 방지

            var articleId = document.getElementById('article-id').value;
            var content = document.getElementById('reply-content').value;

            fetch(`/api/articles/${articleId}/replies`, {
                method: 'POST',
                headers: {
                    'Content-Typ    e': 'application/json',
                },
                body: JSON.stringify({ content: content })
            })
                .then(response => response.json())
                .then(reply => {
                    // 댓글 목록에 새 댓글 추가하는 코드...
                    document.getElementById('reply-content').value = ''; // 입력 필드 초기화
                })
                .catch(error => console.error('Error:', error));
        });
    }
});
