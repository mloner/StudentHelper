PGDMP     ;    1                y            studenthelperdb #   12.6 (Ubuntu 12.6-0ubuntu0.20.04.1)    13.0 h                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            !           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            "           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            #           1262    16384    studenthelperdb    DATABASE     ^   CREATE DATABASE studenthelperdb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US';
    DROP DATABASE studenthelperdb;
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                postgres    false            $           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   postgres    false    3            �            1259    16644 	   Cathedras    TABLE     �   CREATE TABLE public."Cathedras" (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    facultate_id integer NOT NULL,
    location character varying(255) NOT NULL
);
    DROP TABLE public."Cathedras";
       public         heap    postgres    false    3            �            1259    16642    Cathedras_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Cathedras_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public."Cathedras_id_seq";
       public          postgres    false    3    221            %           0    0    Cathedras_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public."Cathedras_id_seq" OWNED BY public."Cathedras".id;
          public          postgres    false    220            �            1259    16636    Classes    TABLE     e   CREATE TABLE public."Classes" (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);
    DROP TABLE public."Classes";
       public         heap    postgres    false    3            �            1259    16634    Classes_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Classes_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public."Classes_id_seq";
       public          postgres    false    219    3            &           0    0    Classes_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public."Classes_id_seq" OWNED BY public."Classes".id;
          public          postgres    false    218            �            1259    16570 
   Facultates    TABLE     h   CREATE TABLE public."Facultates" (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);
     DROP TABLE public."Facultates";
       public         heap    postgres    false    3            �            1259    16568    Facultates_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Facultates_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public."Facultates_id_seq";
       public          postgres    false    203    3            '           0    0    Facultates_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public."Facultates_id_seq" OWNED BY public."Facultates".id;
          public          postgres    false    202            �            1259    16588    Groups    TABLE     �   CREATE TABLE public."Groups" (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    "Facultateid" integer NOT NULL,
    course integer NOT NULL
);
    DROP TABLE public."Groups";
       public         heap    postgres    false    3            �            1259    16586    Groups_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Groups_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public."Groups_id_seq";
       public          postgres    false    3    207            (           0    0    Groups_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public."Groups_id_seq" OWNED BY public."Groups".id;
          public          postgres    false    206            �            1259    16620    LessonTimes    TABLE     �   CREATE TABLE public."LessonTimes" (
    id integer NOT NULL,
    start_time character varying NOT NULL,
    end_time character varying NOT NULL
);
 !   DROP TABLE public."LessonTimes";
       public         heap    postgres    false    3            �            1259    16618    LessonTimes_id_seq    SEQUENCE     �   CREATE SEQUENCE public."LessonTimes_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public."LessonTimes_id_seq";
       public          postgres    false    215    3            )           0    0    LessonTimes_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public."LessonTimes_id_seq" OWNED BY public."LessonTimes".id;
          public          postgres    false    214            �            1259    16628    LessonTypes    TABLE     i   CREATE TABLE public."LessonTypes" (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);
 !   DROP TABLE public."LessonTypes";
       public         heap    postgres    false    3            �            1259    16626    LessonTypes_id_seq    SEQUENCE     �   CREATE SEQUENCE public."LessonTypes_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public."LessonTypes_id_seq";
       public          postgres    false    3    217            *           0    0    LessonTypes_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public."LessonTypes_id_seq" OWNED BY public."LessonTypes".id;
          public          postgres    false    216            �            1259    16612    Lessons    TABLE     �  CREATE TABLE public."Lessons" (
    id integer NOT NULL,
    week_num integer NOT NULL,
    day_num_id integer NOT NULL,
    subject_name_id integer NOT NULL,
    group_id integer NOT NULL,
    subgroup integer NOT NULL,
    teacher_id integer NOT NULL,
    class_id integer NOT NULL,
    lesson_type_id integer NOT NULL,
    start_lesson_num integer NOT NULL,
    lesson_duration integer NOT NULL,
    remote boolean NOT NULL,
    description character varying
);
    DROP TABLE public."Lessons";
       public         heap    postgres    false    3            �            1259    16610    Lessons_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Lessons_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public."Lessons_id_seq";
       public          postgres    false    213    3            +           0    0    Lessons_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public."Lessons_id_seq" OWNED BY public."Lessons".id;
          public          postgres    false    212            �            1259    16604    SubjectNames    TABLE     j   CREATE TABLE public."SubjectNames" (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);
 "   DROP TABLE public."SubjectNames";
       public         heap    postgres    false    3            �            1259    16602    SubjectNames_id_seq    SEQUENCE     �   CREATE SEQUENCE public."SubjectNames_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public."SubjectNames_id_seq";
       public          postgres    false    211    3            ,           0    0    SubjectNames_id_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public."SubjectNames_id_seq" OWNED BY public."SubjectNames".id;
          public          postgres    false    210            �            1259    16596    TeacherDegrees    TABLE     l   CREATE TABLE public."TeacherDegrees" (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);
 $   DROP TABLE public."TeacherDegrees";
       public         heap    postgres    false    3            �            1259    16801    TeacherPositions    TABLE     e   CREATE TABLE public."TeacherPositions" (
    id integer NOT NULL,
    name character varying(255)
);
 &   DROP TABLE public."TeacherPositions";
       public         heap    postgres    false    3            �            1259    16594    TeacherPositions_id_seq    SEQUENCE     �   CREATE SEQUENCE public."TeacherPositions_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public."TeacherPositions_id_seq";
       public          postgres    false    209    3            -           0    0    TeacherPositions_id_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public."TeacherPositions_id_seq" OWNED BY public."TeacherDegrees".id;
          public          postgres    false    208            �            1259    16580    Teachers    TABLE     2  CREATE TABLE public."Teachers" (
    id integer NOT NULL,
    "FIO" character varying(100) NOT NULL,
    "Cathedraid" integer NOT NULL,
    password character varying(255),
    phone character varying(30),
    email character varying(100),
    "TeacherPositionid" integer,
    "TeacherDegreeid" integer
);
    DROP TABLE public."Teachers";
       public         heap    postgres    false    3            �            1259    16578    Teachers_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Teachers_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public."Teachers_id_seq";
       public          postgres    false    205    3            .           0    0    Teachers_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public."Teachers_id_seq" OWNED BY public."Teachers".id;
          public          postgres    false    204            �            1259    16825    WeekDays    TABLE     ]   CREATE TABLE public."WeekDays" (
    id integer NOT NULL,
    name character varying(255)
);
    DROP TABLE public."WeekDays";
       public         heap    postgres    false    3            �            1259    16823    WeekDays_id_seq    SEQUENCE     �   CREATE SEQUENCE public."WeekDays_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public."WeekDays_id_seq";
       public          postgres    false    3    225            /           0    0    WeekDays_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public."WeekDays_id_seq" OWNED BY public."WeekDays".id;
          public          postgres    false    224            �            1259    16799    teacherdegrees_id_seq    SEQUENCE     �   CREATE SEQUENCE public.teacherdegrees_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.teacherdegrees_id_seq;
       public          postgres    false    3    223            0           0    0    teacherdegrees_id_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.teacherdegrees_id_seq OWNED BY public."TeacherPositions".id;
          public          postgres    false    222            ]           2604    16647    Cathedras id    DEFAULT     p   ALTER TABLE ONLY public."Cathedras" ALTER COLUMN id SET DEFAULT nextval('public."Cathedras_id_seq"'::regclass);
 =   ALTER TABLE public."Cathedras" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    220    221    221            \           2604    16639 
   Classes id    DEFAULT     l   ALTER TABLE ONLY public."Classes" ALTER COLUMN id SET DEFAULT nextval('public."Classes_id_seq"'::regclass);
 ;   ALTER TABLE public."Classes" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    218    219    219            T           2604    16573    Facultates id    DEFAULT     r   ALTER TABLE ONLY public."Facultates" ALTER COLUMN id SET DEFAULT nextval('public."Facultates_id_seq"'::regclass);
 >   ALTER TABLE public."Facultates" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    202    203    203            V           2604    16591 	   Groups id    DEFAULT     j   ALTER TABLE ONLY public."Groups" ALTER COLUMN id SET DEFAULT nextval('public."Groups_id_seq"'::regclass);
 :   ALTER TABLE public."Groups" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    206    207    207            Z           2604    16623    LessonTimes id    DEFAULT     t   ALTER TABLE ONLY public."LessonTimes" ALTER COLUMN id SET DEFAULT nextval('public."LessonTimes_id_seq"'::regclass);
 ?   ALTER TABLE public."LessonTimes" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    214    215    215            [           2604    16631    LessonTypes id    DEFAULT     t   ALTER TABLE ONLY public."LessonTypes" ALTER COLUMN id SET DEFAULT nextval('public."LessonTypes_id_seq"'::regclass);
 ?   ALTER TABLE public."LessonTypes" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    217    217            Y           2604    16615 
   Lessons id    DEFAULT     l   ALTER TABLE ONLY public."Lessons" ALTER COLUMN id SET DEFAULT nextval('public."Lessons_id_seq"'::regclass);
 ;   ALTER TABLE public."Lessons" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    212    213    213            X           2604    16607    SubjectNames id    DEFAULT     v   ALTER TABLE ONLY public."SubjectNames" ALTER COLUMN id SET DEFAULT nextval('public."SubjectNames_id_seq"'::regclass);
 @   ALTER TABLE public."SubjectNames" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    210    211    211            W           2604    16599    TeacherDegrees id    DEFAULT     |   ALTER TABLE ONLY public."TeacherDegrees" ALTER COLUMN id SET DEFAULT nextval('public."TeacherPositions_id_seq"'::regclass);
 B   ALTER TABLE public."TeacherDegrees" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    209    208    209            ^           2604    16804    TeacherPositions id    DEFAULT     z   ALTER TABLE ONLY public."TeacherPositions" ALTER COLUMN id SET DEFAULT nextval('public.teacherdegrees_id_seq'::regclass);
 D   ALTER TABLE public."TeacherPositions" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    222    223    223            U           2604    16583    Teachers id    DEFAULT     n   ALTER TABLE ONLY public."Teachers" ALTER COLUMN id SET DEFAULT nextval('public."Teachers_id_seq"'::regclass);
 <   ALTER TABLE public."Teachers" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    205    204    205            _           2604    16828    WeekDays id    DEFAULT     n   ALTER TABLE ONLY public."WeekDays" ALTER COLUMN id SET DEFAULT nextval('public."WeekDays_id_seq"'::regclass);
 <   ALTER TABLE public."WeekDays" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    224    225    225                      0    16644 	   Cathedras 
   TABLE DATA           G   COPY public."Cathedras" (id, name, facultate_id, location) FROM stdin;
    public          postgres    false    221                      0    16636    Classes 
   TABLE DATA           -   COPY public."Classes" (id, name) FROM stdin;
    public          postgres    false    219                      0    16570 
   Facultates 
   TABLE DATA           0   COPY public."Facultates" (id, name) FROM stdin;
    public          postgres    false    203                      0    16588    Groups 
   TABLE DATA           C   COPY public."Groups" (id, name, "Facultateid", course) FROM stdin;
    public          postgres    false    207                      0    16620    LessonTimes 
   TABLE DATA           A   COPY public."LessonTimes" (id, start_time, end_time) FROM stdin;
    public          postgres    false    215                      0    16628    LessonTypes 
   TABLE DATA           1   COPY public."LessonTypes" (id, name) FROM stdin;
    public          postgres    false    217                      0    16612    Lessons 
   TABLE DATA           �   COPY public."Lessons" (id, week_num, day_num_id, subject_name_id, group_id, subgroup, teacher_id, class_id, lesson_type_id, start_lesson_num, lesson_duration, remote, description) FROM stdin;
    public          postgres    false    213                      0    16604    SubjectNames 
   TABLE DATA           2   COPY public."SubjectNames" (id, name) FROM stdin;
    public          postgres    false    211                      0    16596    TeacherDegrees 
   TABLE DATA           4   COPY public."TeacherDegrees" (id, name) FROM stdin;
    public          postgres    false    209                      0    16801    TeacherPositions 
   TABLE DATA           6   COPY public."TeacherPositions" (id, name) FROM stdin;
    public          postgres    false    223            	          0    16580    Teachers 
   TABLE DATA           }   COPY public."Teachers" (id, "FIO", "Cathedraid", password, phone, email, "TeacherPositionid", "TeacherDegreeid") FROM stdin;
    public          postgres    false    205                      0    16825    WeekDays 
   TABLE DATA           .   COPY public."WeekDays" (id, name) FROM stdin;
    public          postgres    false    225            1           0    0    Cathedras_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public."Cathedras_id_seq"', 8, true);
          public          postgres    false    220            2           0    0    Classes_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public."Classes_id_seq"', 14, true);
          public          postgres    false    218            3           0    0    Facultates_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public."Facultates_id_seq"', 2, true);
          public          postgres    false    202            4           0    0    Groups_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public."Groups_id_seq"', 2, true);
          public          postgres    false    206            5           0    0    LessonTimes_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public."LessonTimes_id_seq"', 6, true);
          public          postgres    false    214            6           0    0    LessonTypes_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public."LessonTypes_id_seq"', 3, true);
          public          postgres    false    216            7           0    0    Lessons_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public."Lessons_id_seq"', 28, true);
          public          postgres    false    212            8           0    0    SubjectNames_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public."SubjectNames_id_seq"', 55, true);
          public          postgres    false    210            9           0    0    TeacherPositions_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public."TeacherPositions_id_seq"', 7, true);
          public          postgres    false    208            :           0    0    Teachers_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public."Teachers_id_seq"', 16, true);
          public          postgres    false    204            ;           0    0    WeekDays_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public."WeekDays_id_seq"', 6, true);
          public          postgres    false    224            <           0    0    teacherdegrees_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.teacherdegrees_id_seq', 6, true);
          public          postgres    false    222            w           2606    16649    Cathedras Cathedras_pk 
   CONSTRAINT     X   ALTER TABLE ONLY public."Cathedras"
    ADD CONSTRAINT "Cathedras_pk" PRIMARY KEY (id);
 D   ALTER TABLE ONLY public."Cathedras" DROP CONSTRAINT "Cathedras_pk";
       public            postgres    false    221            u           2606    16641    Classes Classes_pk 
   CONSTRAINT     T   ALTER TABLE ONLY public."Classes"
    ADD CONSTRAINT "Classes_pk" PRIMARY KEY (id);
 @   ALTER TABLE ONLY public."Classes" DROP CONSTRAINT "Classes_pk";
       public            postgres    false    219            a           2606    16577    Facultates Facultates_name_key 
   CONSTRAINT     ]   ALTER TABLE ONLY public."Facultates"
    ADD CONSTRAINT "Facultates_name_key" UNIQUE (name);
 L   ALTER TABLE ONLY public."Facultates" DROP CONSTRAINT "Facultates_name_key";
       public            postgres    false    203            c           2606    16575    Facultates Facultates_pk 
   CONSTRAINT     Z   ALTER TABLE ONLY public."Facultates"
    ADD CONSTRAINT "Facultates_pk" PRIMARY KEY (id);
 F   ALTER TABLE ONLY public."Facultates" DROP CONSTRAINT "Facultates_pk";
       public            postgres    false    203            g           2606    16593    Groups Groups_pk 
   CONSTRAINT     R   ALTER TABLE ONLY public."Groups"
    ADD CONSTRAINT "Groups_pk" PRIMARY KEY (id);
 >   ALTER TABLE ONLY public."Groups" DROP CONSTRAINT "Groups_pk";
       public            postgres    false    207            q           2606    16625    LessonTimes LessonTimes_pk 
   CONSTRAINT     \   ALTER TABLE ONLY public."LessonTimes"
    ADD CONSTRAINT "LessonTimes_pk" PRIMARY KEY (id);
 H   ALTER TABLE ONLY public."LessonTimes" DROP CONSTRAINT "LessonTimes_pk";
       public            postgres    false    215            s           2606    16633    LessonTypes LessonTypes_pk 
   CONSTRAINT     \   ALTER TABLE ONLY public."LessonTypes"
    ADD CONSTRAINT "LessonTypes_pk" PRIMARY KEY (id);
 H   ALTER TABLE ONLY public."LessonTypes" DROP CONSTRAINT "LessonTypes_pk";
       public            postgres    false    217            k           2606    16609    SubjectNames SubjectNames_pk 
   CONSTRAINT     ^   ALTER TABLE ONLY public."SubjectNames"
    ADD CONSTRAINT "SubjectNames_pk" PRIMARY KEY (id);
 J   ALTER TABLE ONLY public."SubjectNames" DROP CONSTRAINT "SubjectNames_pk";
       public            postgres    false    211            i           2606    16601 "   TeacherDegrees TeacherPositions_pk 
   CONSTRAINT     d   ALTER TABLE ONLY public."TeacherDegrees"
    ADD CONSTRAINT "TeacherPositions_pk" PRIMARY KEY (id);
 P   ALTER TABLE ONLY public."TeacherDegrees" DROP CONSTRAINT "TeacherPositions_pk";
       public            postgres    false    209            e           2606    16585    Teachers Teachers_pk 
   CONSTRAINT     V   ALTER TABLE ONLY public."Teachers"
    ADD CONSTRAINT "Teachers_pk" PRIMARY KEY (id);
 B   ALTER TABLE ONLY public."Teachers" DROP CONSTRAINT "Teachers_pk";
       public            postgres    false    205            {           2606    16830    WeekDays WeekDays_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public."WeekDays"
    ADD CONSTRAINT "WeekDays_pkey" PRIMARY KEY (id);
 D   ALTER TABLE ONLY public."WeekDays" DROP CONSTRAINT "WeekDays_pkey";
       public            postgres    false    225            o           2606    16741    Lessons lessons_pk 
   CONSTRAINT     R   ALTER TABLE ONLY public."Lessons"
    ADD CONSTRAINT lessons_pk PRIMARY KEY (id);
 >   ALTER TABLE ONLY public."Lessons" DROP CONSTRAINT lessons_pk;
       public            postgres    false    213            m           2606    16773    SubjectNames subjectnames_un 
   CONSTRAINT     Y   ALTER TABLE ONLY public."SubjectNames"
    ADD CONSTRAINT subjectnames_un UNIQUE (name);
 H   ALTER TABLE ONLY public."SubjectNames" DROP CONSTRAINT subjectnames_un;
       public            postgres    false    211            y           2606    16808 "   TeacherPositions teacherdegrees_pk 
   CONSTRAINT     b   ALTER TABLE ONLY public."TeacherPositions"
    ADD CONSTRAINT teacherdegrees_pk PRIMARY KEY (id);
 N   ALTER TABLE ONLY public."TeacherPositions" DROP CONSTRAINT teacherdegrees_pk;
       public            postgres    false    223            �           2606    16695    Cathedras Cathedras_fk0    FK CONSTRAINT     �   ALTER TABLE ONLY public."Cathedras"
    ADD CONSTRAINT "Cathedras_fk0" FOREIGN KEY (facultate_id) REFERENCES public."Facultates"(id);
 E   ALTER TABLE ONLY public."Cathedras" DROP CONSTRAINT "Cathedras_fk0";
       public          postgres    false    203    221    2915                       2606    16660    Groups Groups_fk0    FK CONSTRAINT     �   ALTER TABLE ONLY public."Groups"
    ADD CONSTRAINT "Groups_fk0" FOREIGN KEY ("Facultateid") REFERENCES public."Facultates"(id);
 ?   ALTER TABLE ONLY public."Groups" DROP CONSTRAINT "Groups_fk0";
       public          postgres    false    207    203    2915            |           2606    16650    Teachers Teachers_fk0    FK CONSTRAINT     �   ALTER TABLE ONLY public."Teachers"
    ADD CONSTRAINT "Teachers_fk0" FOREIGN KEY ("Cathedraid") REFERENCES public."Cathedras"(id);
 C   ALTER TABLE ONLY public."Teachers" DROP CONSTRAINT "Teachers_fk0";
       public          postgres    false    205    2935    221            �           2606    16742    Lessons lessons_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public."Lessons"
    ADD CONSTRAINT lessons_fk FOREIGN KEY (subject_name_id) REFERENCES public."SubjectNames"(id);
 >   ALTER TABLE ONLY public."Lessons" DROP CONSTRAINT lessons_fk;
       public          postgres    false    213    2923    211            �           2606    16757    Lessons lessons_fk_1    FK CONSTRAINT     }   ALTER TABLE ONLY public."Lessons"
    ADD CONSTRAINT lessons_fk_1 FOREIGN KEY (teacher_id) REFERENCES public."Teachers"(id);
 @   ALTER TABLE ONLY public."Lessons" DROP CONSTRAINT lessons_fk_1;
       public          postgres    false    213    205    2917            �           2606    16762    Lessons lessons_fk_2    FK CONSTRAINT     z   ALTER TABLE ONLY public."Lessons"
    ADD CONSTRAINT lessons_fk_2 FOREIGN KEY (class_id) REFERENCES public."Classes"(id);
 @   ALTER TABLE ONLY public."Lessons" DROP CONSTRAINT lessons_fk_2;
       public          postgres    false    213    219    2933            �           2606    16767    Lessons lessons_fk_3    FK CONSTRAINT     �   ALTER TABLE ONLY public."Lessons"
    ADD CONSTRAINT lessons_fk_3 FOREIGN KEY (lesson_type_id) REFERENCES public."LessonTypes"(id);
 @   ALTER TABLE ONLY public."Lessons" DROP CONSTRAINT lessons_fk_3;
       public          postgres    false    217    213    2931            �           2606    16747    Lessons lessons_fk_4    FK CONSTRAINT     y   ALTER TABLE ONLY public."Lessons"
    ADD CONSTRAINT lessons_fk_4 FOREIGN KEY (group_id) REFERENCES public."Groups"(id);
 @   ALTER TABLE ONLY public."Lessons" DROP CONSTRAINT lessons_fk_4;
       public          postgres    false    207    213    2919            �           2606    16831    Lessons lessons_fk_5    FK CONSTRAINT     }   ALTER TABLE ONLY public."Lessons"
    ADD CONSTRAINT lessons_fk_5 FOREIGN KEY (day_num_id) REFERENCES public."WeekDays"(id);
 @   ALTER TABLE ONLY public."Lessons" DROP CONSTRAINT lessons_fk_5;
       public          postgres    false    213    225    2939            �           2606    16836    Lessons lessons_fk_6    FK CONSTRAINT     �   ALTER TABLE ONLY public."Lessons"
    ADD CONSTRAINT lessons_fk_6 FOREIGN KEY (start_lesson_num) REFERENCES public."LessonTimes"(id);
 @   ALTER TABLE ONLY public."Lessons" DROP CONSTRAINT lessons_fk_6;
       public          postgres    false    215    2929    213            }           2606    16810    Teachers teachers_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public."Teachers"
    ADD CONSTRAINT teachers_fk FOREIGN KEY ("TeacherPositionid") REFERENCES public."TeacherPositions"(id);
 @   ALTER TABLE ONLY public."Teachers" DROP CONSTRAINT teachers_fk;
       public          postgres    false    205    2937    223            ~           2606    16815    Teachers teachers_fk_1    FK CONSTRAINT     �   ALTER TABLE ONLY public."Teachers"
    ADD CONSTRAINT teachers_fk_1 FOREIGN KEY ("TeacherDegreeid") REFERENCES public."TeacherDegrees"(id);
 B   ALTER TABLE ONLY public."Teachers" DROP CONSTRAINT teachers_fk_1;
       public          postgres    false    205    2921    209                 x��Rmj�0�m�"H�W��.;L�RZ�`c��-xa���+<�hOv�h7!�-�==I�������Mj��hq���0��l�<�U���ܾj>i��<OmB�AP��T
���H��q�kH�G�xxid��I�Iq�d/�}6���ۏ_��T����\t��==�eiz3���5��ds���*[ڒ;��eO�Z�I��/F笡�ѻ�����^I���}&��J��H,/e��I��V	{Ry�Uw�UiF����A<{��.��|3k�7�`d�         y   x�=���0D��*h���r��K��g�I!�KR���F������#�$�`���w���[*��B����F�퟉rf��U�8'������6�J�0�����vo�liO�m��g�p���5O         "   x�3估����.,�21]�a>W� ��         $   x�3�0�¤�t�͌99����`�=... �*         C   x����0�0LN�T����Q�u:�zk	VI���\[��j��sm�R<����k���C��?�M�         C   x�3�0����.�]�q��ˈ����6 �.츰��.c��6^�� 
��@V?W� P:'J           x�u�ۍ�0E��*����<�����T��k�k0 �
� Ǐˍ[b�D����'�r�@{=�@\���`�i�w�'[��p��7���-�h����=��֣��Q���(��_��^3k0�Y�ɮ�.�C� ��K���t���4��h���BM7.�_�t{
}!D�DwE�'���9oh< ݤ�� !��cP�
��w��D]��'�"�G�v$��Z��m����`����J(��\�#+ө��rS+�Idl���I2��	s��\3�]r�_ *un         )  x��T[r�@�fO��\FBvr���3��o�cW���̑�ӻ�N*�B��lO�L�@*��M
��k$��{�Q^%�F��EH���:��rY�E^�.�����5Y�= ������L��Ů5r��J�`}�R-_(��-�gb�y$G��yU���!�`d/'�>Z9+w\')�cp]�1�}��R~��Lu	~r\��X���3�&�|w�����q�j�3a 6�����m #z#�%��RV�#s*9�x4�^ە!���+��z�^(����sE<����x�� �	?��ֻd�=X�]2ᗀ�<�ɚ��sNR����k��+0�ǕF���(�گ�f8 �Aa�����;R���JɻV$5���K[�i�ɹc���l?hG�v�=pOedfK�s{�MoK�Рݵ_Q�?=Ch�0}AŷM�y:�&��8�>�h������ �_�dQy���f�ni�O��QD���s
3}��M���ݘ�������?����T�����~ h�4e��u��;���j�@�l֒�`S�1Q�~�3���H6         d   x�5���0C��]���/�0Pqd�
	�+8ܞ�gW�S4�pMH?7��sK::����ABĆ���+wߍ�E<nmԓճɇ�"���(����:�         �   x�u�K
�@D�ݧ�B��49�d"� �B�R��H"N�Pu#;�l�4T��љ�F@�-+�yE�a���T:D���<[��ם�e��d�M�@o�Û�o����6���,���������A��N���� ]}oN      	   �  x�u��n�@����Yy�]��-O�TB��qWNj%� $8p U��PѦJ�a�F̮ۨ�B��f��~�+����E��p�>|�𑒐� ���s?�#�FA@^�i�ͪ�xi�Y���	�	�1��_!�g���|d������y7w���0z,"���<v>|�p���j�&�ʩ
Ƶ)'���s����=��C�O!z�$;6�򰜎"mj��Ve�l�$_(b�#��=`U��vpy���-.�"QՏܤ�X!�����?�p��w�q�r���Z9�SD���m�����e[�?"ҋ	|G�.oo�6�6yBk�e`r^dF�*����pQ���rk�(k:;J��61U�=���J�Lcs�yn�n�)��5�l�O½,����Ls��"J�T�LaXQ0�XA�D,$�C�$_����YS5�������	\��<�/���         `   x�-��	�@D�I1��j,fUЋ`+� �k���	�����X���� �ؙ��pi!�Es?(�:���Z�M8��æM�V^$��f��C�B�      h                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            !           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            "           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            #           1262    16384    studenthelperdb    DATABASE     ^   CREATE DATABASE studenthelperdb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US';
    DROP DATABASE studenthelperdb;
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                postgres    false            $           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   postgres    false    3            �            1259    16644 	   Cathedras    TABLE     �   CREATE TABLE public."Cathedras" (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    facultate_id integer NOT NULL,
    location character varying(255) NOT NULL
);
    DROP TABLE public."Cathedras";
       public         heap    postgres    false    3            �            1259    16642    Cathedras_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Cathedras_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public."Cathedras_id_seq";
       public          postgres    false    3    221            %           0    0    Cathedras_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public."Cathedras_id_seq" OWNED BY public."Cathedras".id;
          public          postgres    false    220            �            1259    16636    Classes    TABLE     e   CREATE TABLE public."Classes" (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);
    DROP TABLE public."Classes";
       public         heap    postgres    false    3            �            1259    16634    Classes_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Classes_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public."Classes_id_seq";
       public          postgres    false    219    3            &           0    0    Classes_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public."Classes_id_seq" OWNED BY public."Classes".id;
          public          postgres    false    218            �            1259    16570 
   Facultates    TABLE     h   CREATE TABLE public."Facultates" (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);
     DROP TABLE public."Facultates";
       public         heap    postgres    false    3            �            1259    16568    Facultates_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Facultates_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public."Facultates_id_seq";
       public          postgres    false    203    3            '           0    0    Facultates_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public."Facultates_id_seq" OWNED BY public."Facultates".id;
          public          postgres    false    202            �            1259    16588    Groups    TABLE     �   CREATE TABLE public."Groups" (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    "Facultateid" integer NOT NULL,
    course integer NOT NULL
);
    DROP TABLE public."Groups";
       public         heap    postgres    false    3            �            1259    16586    Groups_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Groups_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public."Groups_id_seq";
       public          postgres    false    3    207            (           0    0    Groups_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public."Groups_id_seq" OWNED BY public."Groups".id;
          public          postgres    false    206            �            1259    16620    LessonTimes    TABLE     �   CREATE TABLE public."LessonTimes" (
    id integer NOT NULL,
    start_time character varying NOT NULL,
    end_time character varying NOT NULL
);
 !   DROP TABLE public."LessonTimes";
       public         heap    postgres    false    3            �            1259    16618    LessonTimes_id_seq    SEQUENCE     �   CREATE SEQUENCE public."LessonTimes_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public."LessonTimes_id_seq";
       public          postgres    false    215    3            )           0    0    LessonTimes_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public."LessonTimes_id_seq" OWNED BY public."LessonTimes".id;
          public          postgres    false    214            �            1259    16628    LessonTypes    TABLE     i   CREATE TABLE public."LessonTypes" (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);
 !   DROP TABLE public."LessonTypes";
       public         heap    postgres    false    3            �            1259    16626    LessonTypes_id_seq    SEQUENCE     �   CREATE SEQUENCE public."LessonTypes_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public."LessonTypes_id_seq";
       public          postgres    false    3    217            *           0    0    LessonTypes_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public."LessonTypes_id_seq" OWNED BY public."LessonTypes".id;
          public          postgres    false    216            �            1259    16612    Lessons    TABLE     �  CREATE TABLE public."Lessons" (
    id integer NOT NULL,
    week_num integer NOT NULL,
    day_num_id integer NOT NULL,
    subject_name_id integer NOT NULL,
    group_id integer NOT NULL,
    subgroup integer NOT NULL,
    teacher_id integer NOT NULL,
    class_id integer NOT NULL,
    lesson_type_id integer NOT NULL,
    start_lesson_num integer NOT NULL,
    lesson_duration integer NOT NULL,
    remote boolean NOT NULL,
    description character varying
);
    DROP TABLE public."Lessons";
       public         heap    postgres    false    3            �            1259    16610    Lessons_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Lessons_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public."Lessons_id_seq";
       public          postgres    false    213    3            +           0    0    Lessons_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public."Lessons_id_seq" OWNED BY public."Lessons".id;
          public          postgres    false    212            �            1259    16604    SubjectNames    TABLE     j   CREATE TABLE public."SubjectNames" (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);
 "   DROP TABLE public."SubjectNames";
       public         heap    postgres    false    3            �            1259    16602    SubjectNames_id_seq    SEQUENCE     �   CREATE SEQUENCE public."SubjectNames_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public."SubjectNames_id_seq";
       public          postgres    false    211    3            ,           0    0    SubjectNames_id_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public."SubjectNames_id_seq" OWNED BY public."SubjectNames".id;
          public          postgres    false    210            �            1259    16596    TeacherDegrees    TABLE     l   CREATE TABLE public."TeacherDegrees" (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);
 $   DROP TABLE public."TeacherDegrees";
       public         heap    postgres    false    3            �            1259    16801    TeacherPositions    TABLE     e   CREATE TABLE public."TeacherPositions" (
    id integer NOT NULL,
    name character varying(255)
);
 &   DROP TABLE public."TeacherPositions";
       public         heap    postgres    false    3            �            1259    16594    TeacherPositions_id_seq    SEQUENCE     �   CREATE SEQUENCE public."TeacherPositions_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public."TeacherPositions_id_seq";
       public          postgres    false    209    3            -           0    0    TeacherPositions_id_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public."TeacherPositions_id_seq" OWNED BY public."TeacherDegrees".id;
          public          postgres    false    208            �            1259    16580    Teachers    TABLE     2  CREATE TABLE public."Teachers" (
    id integer NOT NULL,
    "FIO" character varying(100) NOT NULL,
    "Cathedraid" integer NOT NULL,
    password character varying(255),
    phone character varying(30),
    email character varying(100),
    "TeacherPositionid" integer,
    "TeacherDegreeid" integer
);
    DROP TABLE public."Teachers";
       public         heap    postgres    false    3            �            1259    16578    Teachers_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Teachers_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public."Teachers_id_seq";
       public          postgres    false    205    3            .           0    0    Teachers_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public."Teachers_id_seq" OWNED BY public."Teachers".id;
          public          postgres    false    204            �            1259    16825    WeekDays    TABLE     ]   CREATE TABLE public."WeekDays" (
    id integer NOT NULL,
    name character varying(255)
);
    DROP TABLE public."WeekDays";
       public         heap    postgres    false    3            �            1259    16823    WeekDays_id_seq    SEQUENCE     �   CREATE SEQUENCE public."WeekDays_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public."WeekDays_id_seq";
       public          postgres    false    3    225            /           0    0    WeekDays_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public."WeekDays_id_seq" OWNED BY public."WeekDays".id;
          public          postgres    false    224            �            1259    16799    teacherdegrees_id_seq    SEQUENCE     �   CREATE SEQUENCE public.teacherdegrees_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.teacherdegrees_id_seq;
       public          postgres    false    3    223            0           0    0    teacherdegrees_id_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.teacherdegrees_id_seq OWNED BY public."TeacherPositions".id;
          public          postgres    false    222            ]           2604    16647    Cathedras id    DEFAULT     p   ALTER TABLE ONLY public."Cathedras" ALTER COLUMN id SET DEFAULT nextval('public."Cathedras_id_seq"'::regclass);
 =   ALTER TABLE public."Cathedras" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    220    221    221            \           2604    16639 
   Classes id    DEFAULT     l   ALTER TABLE ONLY public."Classes" ALTER COLUMN id SET DEFAULT nextval('public."Classes_id_seq"'::regclass);
 ;   ALTER TABLE public."Classes" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    218    219    219            T           2604    16573    Facultates id    DEFAULT     r   ALTER TABLE ONLY public."Facultates" ALTER COLUMN id SET DEFAULT nextval('public."Facultates_id_seq"'::regclass);
 >   ALTER TABLE public."Facultates" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    202    203    203            V           2604    16591 	   Groups id    DEFAULT     j   ALTER TABLE ONLY public."Groups" ALTER COLUMN id SET DEFAULT nextval('public."Groups_id_seq"'::regclass);
 :   ALTER TABLE public."Groups" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    206    207    207            Z           2604    16623    LessonTimes id    DEFAULT     t   ALTER TABLE ONLY public."LessonTimes" ALTER COLUMN id SET DEFAULT nextval('public."LessonTimes_id_seq"'::regclass);
 ?   ALTER TABLE public."LessonTimes" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    214    215    215            [           2604    16631    LessonTypes id    DEFAULT     t   ALTER TABLE ONLY public."LessonTypes" ALTER COLUMN id SET DEFAULT nextval('public."LessonTypes_id_seq"'::regclass);
 ?   ALTER TABLE public."LessonTypes" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    217    217            Y           2604    16615 
   Lessons id    DEFAULT     l   ALTER TABLE ONLY public."Lessons" ALTER COLUMN id SET DEFAULT nextval('public."Lessons_id_seq"'::regclass);
 ;   ALTER TABLE public."Lessons" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    212    213    213            X           2604    16607    SubjectNames id    DEFAULT     v   ALTER TABLE ONLY public."SubjectNames" ALTER COLUMN id SET DEFAULT nextval('public."SubjectNames_id_seq"'::regclass);
 @   ALTER TABLE public."SubjectNames" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    210    211    211            W           2604    16599    TeacherDegrees id    DEFAULT     |   ALTER TABLE ONLY public."TeacherDegrees" ALTER COLUMN id SET DEFAULT nextval('public."TeacherPositions_id_seq"'::regclass);
 B   ALTER TABLE public."TeacherDegrees" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    209    208    209            ^           2604    16804    TeacherPositions id    DEFAULT     z   ALTER TABLE ONLY public."TeacherPositions" ALTER COLUMN id SET DEFAULT nextval('public.teacherdegrees_id_seq'::regclass);
 D   ALTER TABLE public."TeacherPositions" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    222    223    223            U           2604    16583    Teachers id    DEFAULT     n   ALTER TABLE ONLY public."Teachers" ALTER COLUMN id SET DEFAULT nextval('public."Teachers_id_seq"'::regclass);
 <   ALTER TABLE public."Teachers" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    205    204    205            _           2604    16828    WeekDays id    DEFAULT     n   ALTER TABLE ONLY public."WeekDays" ALTER COLUMN id SET DEFAULT nextval('public."WeekDays_id_seq"'::regclass);
 <   ALTER TABLE public."WeekDays" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    224    225    225                      0    16644 	   Cathedras 
   TABLE DATA           G   COPY public."Cathedras" (id, name, facultate_id, location) FROM stdin;
    public          postgres    false    221   �                 0    16636    Classes 
   TABLE DATA           -   COPY public."Classes" (id, name) FROM stdin;
    public          postgres    false    219   '                 0    16570 
   Facultates 
   TABLE DATA           0   COPY public."Facultates" (id, name) FROM stdin;
    public          postgres    false    203   �                  0    16588    Groups 
   TABLE DATA           C   COPY public."Groups" (id, name, "Facultateid", course) FROM stdin;
    public          postgres    false    207   ,                  0    16620    LessonTimes 
   TABLE DATA           A   COPY public."LessonTimes" (id, start_time, end_time) FROM stdin;
    public          postgres    false    215   .                  0    16628    LessonTypes 
   TABLE DATA           1   COPY public."LessonTypes" (id, name) FROM stdin;
    public          postgres    false    217   M                  0    16612    Lessons 
   TABLE DATA           �   COPY public."Lessons" (id, week_num, day_num_id, subject_name_id, group_id, subgroup, teacher_id, class_id, lesson_type_id, start_lesson_num, lesson_duration, remote, description) FROM stdin;
    public          postgres    false    213   M                  0    16604    SubjectNames 
   TABLE DATA           2   COPY public."SubjectNames" (id, name) FROM stdin;
    public          postgres    false    211   %                 0    16596    TeacherDegrees 
   TABLE DATA           4   COPY public."TeacherDegrees" (id, name) FROM stdin;
    public          postgres    false    209   3                 0    16801    TeacherPositions 
   TABLE DATA           6   COPY public."TeacherPositions" (id, name) FROM stdin;
    public          postgres    false    223   n        	          0    16580    Teachers 
   TABLE DATA           }   COPY public."Teachers" (id, "FIO", "Cathedraid", password, phone, email, "TeacherPositionid", "TeacherDegreeid") FROM stdin;
    public          postgres    false    205   �                  0    16825    WeekDays 
   TABLE DATA           .   COPY public."WeekDays" (id, name) FROM stdin;
    public          postgres    false    225   �       1           0    0    Cathedras_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public."Cathedras_id_seq"', 8, true);
          public          postgres    false    220            2           0    0    Classes_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public."Classes_id_seq"', 14, true);
          public          postgres    false    218            3           0    0    Facultates_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public."Facultates_id_seq"', 2, true);
          public          postgres    false    202            4           0    0    Groups_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public."Groups_id_seq"', 2, true);
          public          postgres    false    206            5           0    0    LessonTimes_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public."LessonTimes_id_seq"', 6, true);
          public          postgres    false    214            6           0    0    LessonTypes_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public."LessonTypes_id_seq"', 3, true);
          public          postgres    false    216            7           0    0    Lessons_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public."Lessons_id_seq"', 28, true);
          public          postgres    false    212            8           0    0    SubjectNames_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public."SubjectNames_id_seq"', 55, true);
          public          postgres    false    210            9           0    0    TeacherPositions_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public."TeacherPositions_id_seq"', 7, true);
          public          postgres    false    208            :           0    0    Teachers_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public."Teachers_id_seq"', 16, true);
          public          postgres    false    204            ;           0    0    WeekDays_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public."WeekDays_id_seq"', 6, true);
          public          postgres    false    224            <           0    0    teacherdegrees_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.teacherdegrees_id_seq', 6, true);
          public          postgres    false    222            w           2606    16649    Cathedras Cathedras_pk 
   CONSTRAINT     X   ALTER TABLE ONLY public."Cathedras"
    ADD CONSTRAINT "Cathedras_pk" PRIMARY KEY (id);
 D   ALTER TABLE ONLY public."Cathedras" DROP CONSTRAINT "Cathedras_pk";
       public            postgres    false    221            u           2606    16641    Classes Classes_pk 
   CONSTRAINT     T   ALTER TABLE ONLY public."Classes"
    ADD CONSTRAINT "Classes_pk" PRIMARY KEY (id);
 @   ALTER TABLE ONLY public."Classes" DROP CONSTRAINT "Classes_pk";
       public            postgres    false    219            a           2606    16577    Facultates Facultates_name_key 
   CONSTRAINT     ]   ALTER TABLE ONLY public."Facultates"
    ADD CONSTRAINT "Facultates_name_key" UNIQUE (name);
 L   ALTER TABLE ONLY public."Facultates" DROP CONSTRAINT "Facultates_name_key";
       public            postgres    false    203            c           2606    16575    Facultates Facultates_pk 
   CONSTRAINT     Z   ALTER TABLE ONLY public."Facultates"
    ADD CONSTRAINT "Facultates_pk" PRIMARY KEY (id);
 F   ALTER TABLE ONLY public."Facultates" DROP CONSTRAINT "Facultates_pk";
       public            postgres    false    203            g           2606    16593    Groups Groups_pk 
   CONSTRAINT     R   ALTER TABLE ONLY public."Groups"
    ADD CONSTRAINT "Groups_pk" PRIMARY KEY (id);
 >   ALTER TABLE ONLY public."Groups" DROP CONSTRAINT "Groups_pk";
       public            postgres    false    207            q           2606    16625    LessonTimes LessonTimes_pk 
   CONSTRAINT     \   ALTER TABLE ONLY public."LessonTimes"
    ADD CONSTRAINT "LessonTimes_pk" PRIMARY KEY (id);
 H   ALTER TABLE ONLY public."LessonTimes" DROP CONSTRAINT "LessonTimes_pk";
       public            postgres    false    215            s           2606    16633    LessonTypes LessonTypes_pk 
   CONSTRAINT     \   ALTER TABLE ONLY public."LessonTypes"
    ADD CONSTRAINT "LessonTypes_pk" PRIMARY KEY (id);
 H   ALTER TABLE ONLY public."LessonTypes" DROP CONSTRAINT "LessonTypes_pk";
       public            postgres    false    217            k           2606    16609    SubjectNames SubjectNames_pk 
   CONSTRAINT     ^   ALTER TABLE ONLY public."SubjectNames"
    ADD CONSTRAINT "SubjectNames_pk" PRIMARY KEY (id);
 J   ALTER TABLE ONLY public."SubjectNames" DROP CONSTRAINT "SubjectNames_pk";
       public            postgres    false    211            i           2606    16601 "   TeacherDegrees TeacherPositions_pk 
   CONSTRAINT     d   ALTER TABLE ONLY public."TeacherDegrees"
    ADD CONSTRAINT "TeacherPositions_pk" PRIMARY KEY (id);
 P   ALTER TABLE ONLY public."TeacherDegrees" DROP CONSTRAINT "TeacherPositions_pk";
       public            postgres    false    209            e           2606    16585    Teachers Teachers_pk 
   CONSTRAINT     V   ALTER TABLE ONLY public."Teachers"
    ADD CONSTRAINT "Teachers_pk" PRIMARY KEY (id);
 B   ALTER TABLE ONLY public."Teachers" DROP CONSTRAINT "Teachers_pk";
       public            postgres    false    205            {           2606    16830    WeekDays WeekDays_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public."WeekDays"
    ADD CONSTRAINT "WeekDays_pkey" PRIMARY KEY (id);
 D   ALTER TABLE ONLY public."WeekDays" DROP CONSTRAINT "WeekDays_pkey";
       public            postgres    false    225            o           2606    16741    Lessons lessons_pk 
   CONSTRAINT     R   ALTER TABLE ONLY public."Lessons"
    ADD CONSTRAINT lessons_pk PRIMARY KEY (id);
 >   ALTER TABLE ONLY public."Lessons" DROP CONSTRAINT lessons_pk;
       public            postgres    false    213            m           2606    16773    SubjectNames subjectnames_un 
   CONSTRAINT     Y   ALTER TABLE ONLY public."SubjectNames"
    ADD CONSTRAINT subjectnames_un UNIQUE (name);
 H   ALTER TABLE ONLY public."SubjectNames" DROP CONSTRAINT subjectnames_un;
       public            postgres    false    211            y           2606    16808 "   TeacherPositions teacherdegrees_pk 
   CONSTRAINT     b   ALTER TABLE ONLY public."TeacherPositions"
    ADD CONSTRAINT teacherdegrees_pk PRIMARY KEY (id);
 N   ALTER TABLE ONLY public."TeacherPositions" DROP CONSTRAINT teacherdegrees_pk;
       public            postgres    false    223            �           2606    16695    Cathedras Cathedras_fk0    FK CONSTRAINT     �   ALTER TABLE ONLY public."Cathedras"
    ADD CONSTRAINT "Cathedras_fk0" FOREIGN KEY (facultate_id) REFERENCES public."Facultates"(id);
 E   ALTER TABLE ONLY public."Cathedras" DROP CONSTRAINT "Cathedras_fk0";
       public          postgres    false    203    221    2915                       2606    16660    Groups Groups_fk0    FK CONSTRAINT     �   ALTER TABLE ONLY public."Groups"
    ADD CONSTRAINT "Groups_fk0" FOREIGN KEY ("Facultateid") REFERENCES public."Facultates"(id);
 ?   ALTER TABLE ONLY public."Groups" DROP CONSTRAINT "Groups_fk0";
       public          postgres    false    207    203    2915            |           2606    16650    Teachers Teachers_fk0    FK CONSTRAINT     �   ALTER TABLE ONLY public."Teachers"
    ADD CONSTRAINT "Teachers_fk0" FOREIGN KEY ("Cathedraid") REFERENCES public."Cathedras"(id);
 C   ALTER TABLE ONLY public."Teachers" DROP CONSTRAINT "Teachers_fk0";
       public          postgres    false    205    2935    221            �           2606    16742    Lessons lessons_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public."Lessons"
    ADD CONSTRAINT lessons_fk FOREIGN KEY (subject_name_id) REFERENCES public."SubjectNames"(id);
 >   ALTER TABLE ONLY public."Lessons" DROP CONSTRAINT lessons_fk;
       public          postgres    false    213    2923    211            �           2606    16757    Lessons lessons_fk_1    FK CONSTRAINT     }   ALTER TABLE ONLY public."Lessons"
    ADD CONSTRAINT lessons_fk_1 FOREIGN KEY (teacher_id) REFERENCES public."Teachers"(id);
 @   ALTER TABLE ONLY public."Lessons" DROP CONSTRAINT lessons_fk_1;
       public          postgres    false    213    205    2917            �           2606    16762    Lessons lessons_fk_2    FK CONSTRAINT     z   ALTER TABLE ONLY public."Lessons"
    ADD CONSTRAINT lessons_fk_2 FOREIGN KEY (class_id) REFERENCES public."Classes"(id);
 @   ALTER TABLE ONLY public."Lessons" DROP CONSTRAINT lessons_fk_2;
       public          postgres    false    213    219    2933            �           2606    16767    Lessons lessons_fk_3    FK CONSTRAINT     �   ALTER TABLE ONLY public."Lessons"
    ADD CONSTRAINT lessons_fk_3 FOREIGN KEY (lesson_type_id) REFERENCES public."LessonTypes"(id);
 @   ALTER TABLE ONLY public."Lessons" DROP CONSTRAINT lessons_fk_3;
       public          postgres    false    217    213    2931            �           2606    16747    Lessons lessons_fk_4    FK CONSTRAINT     y   ALTER TABLE ONLY public."Lessons"
    ADD CONSTRAINT lessons_fk_4 FOREIGN KEY (group_id) REFERENCES public."Groups"(id);
 @   ALTER TABLE ONLY public."Lessons" DROP CONSTRAINT lessons_fk_4;
       public          postgres    false    207    213    2919            �           2606    16831    Lessons lessons_fk_5    FK CONSTRAINT     }   ALTER TABLE ONLY public."Lessons"
    ADD CONSTRAINT lessons_fk_5 FOREIGN KEY (day_num_id) REFERENCES public."WeekDays"(id);
 @   ALTER TABLE ONLY public."Lessons" DROP CONSTRAINT lessons_fk_5;
       public          postgres    false    213    225    2939            �           2606    16836    Lessons lessons_fk_6    FK CONSTRAINT     �   ALTER TABLE ONLY public."Lessons"
    ADD CONSTRAINT lessons_fk_6 FOREIGN KEY (start_lesson_num) REFERENCES public."LessonTimes"(id);
 @   ALTER TABLE ONLY public."Lessons" DROP CONSTRAINT lessons_fk_6;
       public          postgres    false    215    2929    213            }           2606    16810    Teachers teachers_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public."Teachers"
    ADD CONSTRAINT teachers_fk FOREIGN KEY ("TeacherPositionid") REFERENCES public."TeacherPositions"(id);
 @   ALTER TABLE ONLY public."Teachers" DROP CONSTRAINT teachers_fk;
       public          postgres    false    205    2937    223            ~           2606    16815    Teachers teachers_fk_1    FK CONSTRAINT     �   ALTER TABLE ONLY public."Teachers"
    ADD CONSTRAINT teachers_fk_1 FOREIGN KEY ("TeacherDegreeid") REFERENCES public."TeacherDegrees"(id);
 B   ALTER TABLE ONLY public."Teachers" DROP CONSTRAINT teachers_fk_1;
       public          postgres    false    205    2921    209           