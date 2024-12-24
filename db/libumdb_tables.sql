--
-- PostgreSQL database dump
--
--
-- Name: authors; Type: TABLE; Schema: public; Owner: kirixo
--

CREATE TABLE public.authors (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.authors OWNER TO kirixo;

--
-- Name: authors_id_seq; Type: SEQUENCE; Schema: public; Owner: kirixo
--

CREATE SEQUENCE public.authors_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.authors_id_seq OWNER TO kirixo;

--
-- Name: authors_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kirixo
--

ALTER SEQUENCE public.authors_id_seq OWNED BY public.authors.id;


--
-- Name: book_files; Type: TABLE; Schema: public; Owner: kirixo
--

CREATE TABLE public.book_files (
    id integer NOT NULL,
    path character varying(255) NOT NULL,
    book_id integer NOT NULL,
    type character varying(255) NOT NULL
);


ALTER TABLE public.book_files OWNER TO kirixo;

--
-- Name: book_files_id_seq; Type: SEQUENCE; Schema: public; Owner: kirixo
--

CREATE SEQUENCE public.book_files_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.book_files_id_seq OWNER TO kirixo;

--
-- Name: book_files_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kirixo
--

ALTER SEQUENCE public.book_files_id_seq OWNED BY public.book_files.id;


--
-- Name: book_genre; Type: TABLE; Schema: public; Owner: kirixo
--

CREATE TABLE public.book_genre (
    id integer NOT NULL,
    book_id integer NOT NULL,
    genre_id integer NOT NULL
);


ALTER TABLE public.book_genre OWNER TO kirixo;

--
-- Name: book_genre_id_seq; Type: SEQUENCE; Schema: public; Owner: kirixo
--

CREATE SEQUENCE public.book_genre_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.book_genre_id_seq OWNER TO kirixo;

--
-- Name: book_genre_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kirixo
--

ALTER SEQUENCE public.book_genre_id_seq OWNED BY public.book_genre.id;


--
-- Name: books; Type: TABLE; Schema: public; Owner: kirixo
--

CREATE TABLE public.books (
    id integer NOT NULL,
    title character varying(255) NOT NULL,
    description text,
    language_id bigint,
    authors character varying(255),
    year smallint,
    pages_cout integer,
    price numeric(8,2) NOT NULL,
    cover character varying(255)
);


ALTER TABLE public.books OWNER TO kirixo;

--
-- Name: books_id_seq; Type: SEQUENCE; Schema: public; Owner: kirixo
--

CREATE SEQUENCE public.books_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.books_id_seq OWNER TO kirixo;

--
-- Name: books_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kirixo
--

ALTER SEQUENCE public.books_id_seq OWNED BY public.books.id;


--
-- Name: carts; Type: TABLE; Schema: public; Owner: kirixo
--

CREATE TABLE public.carts (
    user_id integer NOT NULL,
    discount numeric(8,2) NOT NULL
);


ALTER TABLE public.carts OWNER TO kirixo;

--
-- Name: carts_content; Type: TABLE; Schema: public; Owner: kirixo
--

CREATE TABLE public.carts_content (
    id integer NOT NULL,
    user_id integer NOT NULL,
    book_id integer NOT NULL
);


ALTER TABLE public.carts_content OWNER TO kirixo;

--
-- Name: carts_content_id_seq; Type: SEQUENCE; Schema: public; Owner: kirixo
--

CREATE SEQUENCE public.carts_content_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.carts_content_id_seq OWNER TO kirixo;

--
-- Name: carts_content_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kirixo
--

ALTER SEQUENCE public.carts_content_id_seq OWNED BY public.carts_content.id;


--
-- Name: favorites; Type: TABLE; Schema: public; Owner: kirixo
--

CREATE TABLE public.favorites (
    id integer NOT NULL,
    user_id integer NOT NULL,
    book_id integer NOT NULL
);


ALTER TABLE public.favorites OWNER TO kirixo;

--
-- Name: favorites_id_seq; Type: SEQUENCE; Schema: public; Owner: kirixo
--

CREATE SEQUENCE public.favorites_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.favorites_id_seq OWNER TO kirixo;

--
-- Name: favorites_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kirixo
--

ALTER SEQUENCE public.favorites_id_seq OWNED BY public.favorites.id;


--
-- Name: genres; Type: TABLE; Schema: public; Owner: kirixo
--

CREATE TABLE public.genres (
    id integer NOT NULL,
    genre_name character varying(255) NOT NULL
);


ALTER TABLE public.genres OWNER TO kirixo;

--
-- Name: genres_id_seq; Type: SEQUENCE; Schema: public; Owner: kirixo
--

CREATE SEQUENCE public.genres_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.genres_id_seq OWNER TO kirixo;

--
-- Name: genres_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kirixo
--

ALTER SEQUENCE public.genres_id_seq OWNED BY public.genres.id;


--
-- Name: languages; Type: TABLE; Schema: public; Owner: kirixo
--

CREATE TABLE public.languages (
    id integer NOT NULL,
    lang_name character varying(255) NOT NULL
);


ALTER TABLE public.languages OWNER TO kirixo;

--
-- Name: languages_id_seq; Type: SEQUENCE; Schema: public; Owner: kirixo
--

CREATE SEQUENCE public.languages_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.languages_id_seq OWNER TO kirixo;

--
-- Name: languages_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kirixo
--

ALTER SEQUENCE public.languages_id_seq OWNED BY public.languages.id;


--
-- Name: lists; Type: TABLE; Schema: public; Owner: kirixo
--

CREATE TABLE public.lists (
    id integer NOT NULL,
    user_id integer NOT NULL,
    book_id integer NOT NULL,
    status_id integer NOT NULL
);


ALTER TABLE public.lists OWNER TO kirixo;

--
-- Name: lists_id_seq; Type: SEQUENCE; Schema: public; Owner: kirixo
--

CREATE SEQUENCE public.lists_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.lists_id_seq OWNER TO kirixo;

--
-- Name: lists_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kirixo
--

ALTER SEQUENCE public.lists_id_seq OWNED BY public.lists.id;


--
-- Name: orders; Type: TABLE; Schema: public; Owner: kirixo
--

CREATE TABLE public.orders (
    id integer NOT NULL,
    user_id integer,
    cost numeric(8,2) NOT NULL,
    date date NOT NULL
);


ALTER TABLE public.orders OWNER TO kirixo;

--
-- Name: orders_content; Type: TABLE; Schema: public; Owner: kirixo
--

CREATE TABLE public.orders_content (
    id integer NOT NULL,
    order_id integer NOT NULL,
    book_id integer
);


ALTER TABLE public.orders_content OWNER TO kirixo;

--
-- Name: orders_content_id_seq; Type: SEQUENCE; Schema: public; Owner: kirixo
--

CREATE SEQUENCE public.orders_content_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.orders_content_id_seq OWNER TO kirixo;

--
-- Name: orders_content_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kirixo
--

ALTER SEQUENCE public.orders_content_id_seq OWNED BY public.orders_content.id;


--
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: kirixo
--

CREATE SEQUENCE public.orders_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.orders_id_seq OWNER TO kirixo;

--
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kirixo
--

ALTER SEQUENCE public.orders_id_seq OWNED BY public.orders.id;


--
-- Name: quotes; Type: TABLE; Schema: public; Owner: kirixo
--

CREATE TABLE public.quotes (
    id integer NOT NULL,
    book_id integer NOT NULL,
    start bigint NOT NULL,
    "end" bigint NOT NULL,
    users_id integer NOT NULL
);


ALTER TABLE public.quotes OWNER TO kirixo;

--
-- Name: quotes_id_seq; Type: SEQUENCE; Schema: public; Owner: kirixo
--

CREATE SEQUENCE public.quotes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.quotes_id_seq OWNER TO kirixo;

--
-- Name: quotes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kirixo
--

ALTER SEQUENCE public.quotes_id_seq OWNED BY public.quotes.id;


--
-- Name: scores; Type: TABLE; Schema: public; Owner: kirixo
--

CREATE TABLE public.scores (
    user_id integer NOT NULL,
    books_id integer NOT NULL,
    value numeric(1,1) NOT NULL
);


ALTER TABLE public.scores OWNER TO kirixo;

--
-- Name: statuses; Type: TABLE; Schema: public; Owner: kirixo
--

CREATE TABLE public.statuses (
    id integer NOT NULL,
    status_name character varying(255) NOT NULL
);


ALTER TABLE public.statuses OWNER TO kirixo;

--
-- Name: statuses_id_seq; Type: SEQUENCE; Schema: public; Owner: kirixo
--

CREATE SEQUENCE public.statuses_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.statuses_id_seq OWNER TO kirixo;

--
-- Name: statuses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kirixo
--

ALTER SEQUENCE public.statuses_id_seq OWNED BY public.statuses.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: kirixo
--

CREATE TABLE public.users (
    id integer NOT NULL,
    email character varying(255),
    avatar character varying(255),
    login character varying(255),
    password character varying(255),
    is_admin boolean DEFAULT false NOT NULL
);


ALTER TABLE public.users OWNER TO kirixo;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: kirixo
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO kirixo;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kirixo
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: authors id; Type: DEFAULT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.authors ALTER COLUMN id SET DEFAULT nextval('public.authors_id_seq'::regclass);


--
-- Name: book_files id; Type: DEFAULT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.book_files ALTER COLUMN id SET DEFAULT nextval('public.book_files_id_seq'::regclass);


--
-- Name: book_genre id; Type: DEFAULT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.book_genre ALTER COLUMN id SET DEFAULT nextval('public.book_genre_id_seq'::regclass);


--
-- Name: books id; Type: DEFAULT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.books ALTER COLUMN id SET DEFAULT nextval('public.books_id_seq'::regclass);


--
-- Name: carts_content id; Type: DEFAULT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.carts_content ALTER COLUMN id SET DEFAULT nextval('public.carts_content_id_seq'::regclass);


--
-- Name: favorites id; Type: DEFAULT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.favorites ALTER COLUMN id SET DEFAULT nextval('public.favorites_id_seq'::regclass);


--
-- Name: genres id; Type: DEFAULT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.genres ALTER COLUMN id SET DEFAULT nextval('public.genres_id_seq'::regclass);


--
-- Name: languages id; Type: DEFAULT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.languages ALTER COLUMN id SET DEFAULT nextval('public.languages_id_seq'::regclass);


--
-- Name: lists id; Type: DEFAULT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.lists ALTER COLUMN id SET DEFAULT nextval('public.lists_id_seq'::regclass);


--
-- Name: orders id; Type: DEFAULT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq'::regclass);


--
-- Name: orders_content id; Type: DEFAULT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.orders_content ALTER COLUMN id SET DEFAULT nextval('public.orders_content_id_seq'::regclass);


--
-- Name: quotes id; Type: DEFAULT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.quotes ALTER COLUMN id SET DEFAULT nextval('public.quotes_id_seq'::regclass);


--
-- Name: statuses id; Type: DEFAULT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.statuses ALTER COLUMN id SET DEFAULT nextval('public.statuses_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Name: authors authors_pk; Type: CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.authors
    ADD CONSTRAINT authors_pk PRIMARY KEY (id);


--
-- Name: book_files book_files_pk; Type: CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.book_files
    ADD CONSTRAINT book_files_pk PRIMARY KEY (id);


--
-- Name: book_genre book_genre_pk; Type: CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.book_genre
    ADD CONSTRAINT book_genre_pk PRIMARY KEY (id);


--
-- Name: books books_pk; Type: CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_pk PRIMARY KEY (id);


--
-- Name: carts_content carts_content_pk; Type: CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.carts_content
    ADD CONSTRAINT carts_content_pk PRIMARY KEY (id);


--
-- Name: carts carts_pk; Type: CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.carts
    ADD CONSTRAINT carts_pk PRIMARY KEY (user_id);


--
-- Name: favorites favorites_pk; Type: CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.favorites
    ADD CONSTRAINT favorites_pk PRIMARY KEY (id);


--
-- Name: genres genres_pk; Type: CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.genres
    ADD CONSTRAINT genres_pk PRIMARY KEY (id);


--
-- Name: languages languages_pk; Type: CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.languages
    ADD CONSTRAINT languages_pk PRIMARY KEY (id);


--
-- Name: lists lists_pk; Type: CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.lists
    ADD CONSTRAINT lists_pk PRIMARY KEY (id);


--
-- Name: orders_content orders_content_pk; Type: CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.orders_content
    ADD CONSTRAINT orders_content_pk PRIMARY KEY (id);


--
-- Name: orders orders_pk; Type: CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pk PRIMARY KEY (id);


--
-- Name: quotes quotes_pk; Type: CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.quotes
    ADD CONSTRAINT quotes_pk PRIMARY KEY (id);


--
-- Name: scores scores_pk; Type: CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.scores
    ADD CONSTRAINT scores_pk PRIMARY KEY (user_id, books_id);


--
-- Name: statuses statuses_pk; Type: CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.statuses
    ADD CONSTRAINT statuses_pk PRIMARY KEY (id);


--
-- Name: users users_pk; Type: CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);


--
-- Name: book_files book_files_book_id_foreign; Type: FK CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.book_files
    ADD CONSTRAINT book_files_book_id_foreign FOREIGN KEY (book_id) REFERENCES public.books(id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- Name: book_genre book_genre_book_id_foreign; Type: FK CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.book_genre
    ADD CONSTRAINT book_genre_book_id_foreign FOREIGN KEY (book_id) REFERENCES public.books(id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- Name: book_genre book_genre_genre_id_foreign; Type: FK CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.book_genre
    ADD CONSTRAINT book_genre_genre_id_foreign FOREIGN KEY (genre_id) REFERENCES public.genres(id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- Name: books books_language_id_foreign; Type: FK CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_language_id_foreign FOREIGN KEY (language_id) REFERENCES public.languages(id) ON UPDATE RESTRICT ON DELETE SET NULL;


--
-- Name: carts_content carts_content_book_id_foreign; Type: FK CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.carts_content
    ADD CONSTRAINT carts_content_book_id_foreign FOREIGN KEY (book_id) REFERENCES public.books(id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- Name: carts_content carts_content_user_id_foreign; Type: FK CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.carts_content
    ADD CONSTRAINT carts_content_user_id_foreign FOREIGN KEY (user_id) REFERENCES public.users(id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- Name: favorites favorites_book_id_foreign; Type: FK CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.favorites
    ADD CONSTRAINT favorites_book_id_foreign FOREIGN KEY (book_id) REFERENCES public.books(id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- Name: favorites favorites_user_id_foreign; Type: FK CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.favorites
    ADD CONSTRAINT favorites_user_id_foreign FOREIGN KEY (user_id) REFERENCES public.users(id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- Name: lists lists_book_id_foreign; Type: FK CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.lists
    ADD CONSTRAINT lists_book_id_foreign FOREIGN KEY (book_id) REFERENCES public.books(id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- Name: lists lists_status_id_foreign; Type: FK CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.lists
    ADD CONSTRAINT lists_status_id_foreign FOREIGN KEY (status_id) REFERENCES public.statuses(id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- Name: lists lists_user_id_foreign; Type: FK CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.lists
    ADD CONSTRAINT lists_user_id_foreign FOREIGN KEY (user_id) REFERENCES public.users(id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- Name: orders_content orders_content_book_id_foreign; Type: FK CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.orders_content
    ADD CONSTRAINT orders_content_book_id_foreign FOREIGN KEY (book_id) REFERENCES public.books(id) ON UPDATE RESTRICT ON DELETE SET NULL;


--
-- Name: orders_content orders_content_order_id_foreign; Type: FK CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.orders_content
    ADD CONSTRAINT orders_content_order_id_foreign FOREIGN KEY (order_id) REFERENCES public.orders(id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- Name: orders orders_user_id_foreign; Type: FK CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_user_id_foreign FOREIGN KEY (user_id) REFERENCES public.users(id) ON UPDATE RESTRICT ON DELETE SET NULL;


--
-- Name: quotes quotes_book_id_foreign; Type: FK CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.quotes
    ADD CONSTRAINT quotes_book_id_foreign FOREIGN KEY (book_id) REFERENCES public.books(id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- Name: quotes quotes_users; Type: FK CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.quotes
    ADD CONSTRAINT quotes_users FOREIGN KEY (users_id) REFERENCES public.users(id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- Name: scores scores_books; Type: FK CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.scores
    ADD CONSTRAINT scores_books FOREIGN KEY (books_id) REFERENCES public.books(id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- Name: scores scores_users; Type: FK CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.scores
    ADD CONSTRAINT scores_users FOREIGN KEY (user_id) REFERENCES public.users(id) ON UPDATE RESTRICT ON DELETE SET NULL;


--
-- Name: carts users_id_foreign; Type: FK CONSTRAINT; Schema: public; Owner: kirixo
--

ALTER TABLE ONLY public.carts
    ADD CONSTRAINT users_id_foreign FOREIGN KEY (user_id) REFERENCES public.users(id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

