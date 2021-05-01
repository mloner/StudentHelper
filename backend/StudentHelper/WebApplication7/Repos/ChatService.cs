using MongoDB.Driver;
using StudentHelper.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace StudentHelper.Repos
{
    public class ChatService
    {
        private readonly IMongoCollection<Chat> _chats;

        public ChatService(IChatstoreDatabaseSettings settings)
        {
            var client = new MongoClient(settings.ConnectionString);
            var database = client.GetDatabase(settings.DatabaseName);

            _chats = database.GetCollection<Chat>(settings.ChatsCollectionName);
        }

        public List<Chat> Get() =>
            _chats.Find(chat => true).ToList();

        public List<Chat> GetByGroup(string group)
        {
            return _chats.Find<Chat>(chat => chat.group == group).ToList();
        }

        public List<Chat> GetByTeacher(string prepodName)
        {
            return _chats.Find<Chat>(chat => chat.prepodName == prepodName).ToList();
        }

        
        public Chat GetByTeacherByGroupByLessonName(string teacher, string group, string lessonName)
        {
            return _chats.Find<Chat>(chat => chat.prepodName == teacher &&
                                             chat.group == group &&
                                             chat.lessonName == lessonName).FirstOrDefault();
        }

        public Chat Get(string id) =>
            _chats.Find<Chat>(chat => chat.Id == id).FirstOrDefault();

        public Chat Create(Chat chat)
        {
            _chats.InsertOne(chat);
            return chat;
        }

        public void Update(string id, Chat chatIn) =>
            _chats.ReplaceOne(chat => chat.Id == id, chatIn);

        public void Remove(Chat chatIn) =>
            _chats.DeleteOne(chat => chat.Id == chatIn.Id);

        public void Remove(string id) =>
            _chats.DeleteOne(chat => chat.Id == id);
    }
}
