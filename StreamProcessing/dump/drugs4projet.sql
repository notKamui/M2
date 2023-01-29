--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.19
-- Dumped by pg_dump version 9.5.19

SET statement_timeout = 0;
SET lock_timeout = 0;
--SET client_encoding = 'LATIN1';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

CREATE TABLE public.drugs4projet (
                                     cip integer NOT NULL,
                                     prix real
);

INSERT INTO public.drugs4projet VALUES (3202069, 3.96000004);
INSERT INTO public.drugs4projet VALUES (3204654, 3.98000002);
INSERT INTO public.drugs4projet VALUES (3060310, 6.28000021);
INSERT INTO public.drugs4projet VALUES (3885890, 2.45000005);
INSERT INTO public.drugs4projet VALUES (3098614, 3.20000005);
INSERT INTO public.drugs4projet VALUES (3059258, 4.71999979);
INSERT INTO public.drugs4projet VALUES (3123387, 5.32999992);
INSERT INTO public.drugs4projet VALUES (7375789, 5.5999999);
INSERT INTO public.drugs4projet VALUES (3335250, 6.61999989);
INSERT INTO public.drugs4projet VALUES (3325406, 3.83999991);
INSERT INTO public.drugs4projet VALUES (3060988, 4.88000011);
INSERT INTO public.drugs4projet VALUES (3202537, 3.04999995);
INSERT INTO public.drugs4projet VALUES (3626102, 5.44000006);
INSERT INTO public.drugs4projet VALUES (3786018, 2.45000005);
INSERT INTO public.drugs4projet VALUES (3370507, 4.57000017);
INSERT INTO public.drugs4projet VALUES (3402561, 7.30000019);
INSERT INTO public.drugs4projet VALUES (3219756, 4.19000006);
INSERT INTO public.drugs4projet VALUES (3336195, 5.32999992);
INSERT INTO public.drugs4projet VALUES (3846217, 3.22000003);
INSERT INTO public.drugs4projet VALUES (3605896, 2.67000008);
INSERT INTO public.drugs4projet VALUES (3421819, 2.36999989);
INSERT INTO public.drugs4projet VALUES (3010960, 3.52999997);
INSERT INTO public.drugs4projet VALUES (3484085, 4.28999996);
INSERT INTO public.drugs4projet VALUES (2176424, 3.00999999);
INSERT INTO public.drugs4projet VALUES (7488051, 7.5);
INSERT INTO public.drugs4projet VALUES (4924445, 3.00999999);
INSERT INTO public.drugs4projet VALUES (3597464, 2.98000002);
INSERT INTO public.drugs4projet VALUES (4913393, 3.72000003);
INSERT INTO public.drugs4projet VALUES (3292463, 3.88000011);
INSERT INTO public.drugs4projet VALUES (3641662, 3.11999989);
INSERT INTO public.drugs4projet VALUES (2678249, 3.52999997);
INSERT INTO public.drugs4projet VALUES (3825037, 2.45000005);
INSERT INTO public.drugs4projet VALUES (3202939, 3.95000005);
INSERT INTO public.drugs4projet VALUES (3370387, 4.57000017);
INSERT INTO public.drugs4projet VALUES (2783851, 3.52999997);
INSERT INTO public.drugs4projet VALUES (2783868, 3.52999997);
INSERT INTO public.drugs4projet VALUES (3061752, 5.17999983);
INSERT INTO public.drugs4projet VALUES (4924646, 3.00999999);
INSERT INTO public.drugs4projet VALUES (3098608, 3.16000009);
INSERT INTO public.drugs4projet VALUES (3691849, 3.86999989);
INSERT INTO public.drugs4projet VALUES (3370476, 4.57000017);
INSERT INTO public.drugs4projet VALUES (3846200, 2.3499999);
INSERT INTO public.drugs4projet VALUES (3941246, 2.5999999);
INSERT INTO public.drugs4projet VALUES (4921518, 3.00999999);
INSERT INTO public.drugs4projet VALUES (3697148, 2.45000005);
INSERT INTO public.drugs4projet VALUES (3119782, 5.17999983);
INSERT INTO public.drugs4projet VALUES (3941080, 2.5999999);
INSERT INTO public.drugs4projet VALUES (3001199, 3.52999997);
INSERT INTO public.drugs4projet VALUES (3078385, 5.03000021);
INSERT INTO public.drugs4projet VALUES (3311143, 4.28999996);
INSERT INTO public.drugs4projet VALUES (3356217, 3);
INSERT INTO public.drugs4projet VALUES (3646814, 5.9000001);
INSERT INTO public.drugs4projet VALUES (4921530, 3.00999999);
INSERT INTO public.drugs4projet VALUES (3468146, 2.5999999);
INSERT INTO public.drugs4projet VALUES (3490418, 4.28999996);
INSERT INTO public.drugs4projet VALUES (3370542, 4.57000017);
INSERT INTO public.drugs4projet VALUES (4926912, 3.00999999);
INSERT INTO public.drugs4projet VALUES (3001202, 3.52999997);
INSERT INTO public.drugs4projet VALUES (2667435, 3.52999997);
INSERT INTO public.drugs4projet VALUES (3186301, 2.45000005);
INSERT INTO public.drugs4projet VALUES (4924669, 3.00999999);
INSERT INTO public.drugs4projet VALUES (3689574, 2.5999999);
INSERT INTO public.drugs4projet VALUES (3002694, 3.03999996);
INSERT INTO public.drugs4projet VALUES (2783874, 3.52999997);
INSERT INTO public.drugs4projet VALUES (3101109, 2.32999992);
INSERT INTO public.drugs4projet VALUES (3246262, 2.75);
INSERT INTO public.drugs4projet VALUES (3309525, 3.03999996);
INSERT INTO public.drugs4projet VALUES (3352478, 4.6500001);
INSERT INTO public.drugs4projet VALUES (3117820, 3.06999993);
INSERT INTO public.drugs4projet VALUES (3049567, 3.17000008);
INSERT INTO public.drugs4projet VALUES (3697295, 2.45000005);
INSERT INTO public.drugs4projet VALUES (3501300, 3.19000006);
INSERT INTO public.drugs4projet VALUES (3679096, 2.72000003);
INSERT INTO public.drugs4projet VALUES (3031900, 3.55999994);
INSERT INTO public.drugs4projet VALUES (3060327, 6.3499999);
INSERT INTO public.drugs4projet VALUES (3049538, 3.33999991);
INSERT INTO public.drugs4projet VALUES (2240085, 3.00999999);
INSERT INTO public.drugs4projet VALUES (3689545, 3.00999999);
INSERT INTO public.drugs4projet VALUES (3370559, 4.57000017);
INSERT INTO public.drugs4projet VALUES (3940991, 2.5999999);
INSERT INTO public.drugs4projet VALUES (3697088, 2.45000005);
INSERT INTO public.drugs4projet VALUES (2792749, 3.03999996);
INSERT INTO public.drugs4projet VALUES (3446417, 4.17999983);
INSERT INTO public.drugs4projet VALUES (3604307, 3.6099999);
INSERT INTO public.drugs4projet VALUES (4922127, 3.00999999);
INSERT INTO public.drugs4projet VALUES (2240079, 3.00999999);
INSERT INTO public.drugs4projet VALUES (4919148, 3.00999999);
INSERT INTO public.drugs4projet VALUES (3490370, 4.07999992);
INSERT INTO public.drugs4projet VALUES (3370482, 4.57000017);
INSERT INTO public.drugs4projet VALUES (3370192, 4.57000017);
INSERT INTO public.drugs4projet VALUES (3052658, 5.17999983);
INSERT INTO public.drugs4projet VALUES (3059169, 6.96999979);
INSERT INTO public.drugs4projet VALUES (4168624, 2.6099999);
INSERT INTO public.drugs4projet VALUES (3060356, 5.03000021);
INSERT INTO public.drugs4projet VALUES (2238556, 2.8499999);
INSERT INTO public.drugs4projet VALUES (3071615, 5.63000011);
INSERT INTO public.drugs4projet VALUES (4924675, 3.00999999);
INSERT INTO public.drugs4projet VALUES (3042855, 4.36000013);
INSERT INTO public.drugs4projet VALUES (2792703, 3.03999996);
INSERT INTO public.drugs4projet VALUES (2790992, 3.03999996);
INSERT INTO public.drugs4projet VALUES (3413613, 2.88000011);
INSERT INTO public.drugs4projet VALUES (3244027, 5.42000008);
INSERT INTO public.drugs4projet VALUES (3049544, 2.42000008);
INSERT INTO public.drugs4projet VALUES (3490387, 6.03999996);
INSERT INTO public.drugs4projet VALUES (3006437, 3.03999996);
INSERT INTO public.drugs4projet VALUES (3626094, 9.85000038);
INSERT INTO public.drugs4projet VALUES (3066933, 4.32999992);
INSERT INTO public.drugs4projet VALUES (4924623, 3.00999999);
INSERT INTO public.drugs4projet VALUES (3010022, 5.17999983);
INSERT INTO public.drugs4projet VALUES (4924681, 3.00999999);
INSERT INTO public.drugs4projet VALUES (3940962, 2.5999999);
INSERT INTO public.drugs4projet VALUES (3001200, 3.52999997);
INSERT INTO public.drugs4projet VALUES (3288970, 3.13000011);
INSERT INTO public.drugs4projet VALUES (3484079, 2.52999997);
INSERT INTO public.drugs4projet VALUES (4921553, 3.00999999);
INSERT INTO public.drugs4projet VALUES (3370140, 4.57000017);
INSERT INTO public.drugs4projet VALUES (3370499, 4.57000017);
INSERT INTO public.drugs4projet VALUES (2225720, 3.71000004);
INSERT INTO public.drugs4projet VALUES (3228502, 2.67000008);
INSERT INTO public.drugs4projet VALUES (3245400, 4.80000019);
INSERT INTO public.drugs4projet VALUES (3372848, 3.28999996);
INSERT INTO public.drugs4projet VALUES (7375766, 4.80000019);
INSERT INTO public.drugs4projet VALUES (3468181, 2.5999999);
INSERT INTO public.drugs4projet VALUES (3745611, 2.67000008);
INSERT INTO public.drugs4projet VALUES (3946172, 2.45000005);
INSERT INTO public.drugs4projet VALUES (3468086, 2.5999999);
INSERT INTO public.drugs4projet VALUES (2238562, 4);
INSERT INTO public.drugs4projet VALUES (3059353, 6.96999979);
INSERT INTO public.drugs4projet VALUES (2783584, 3.52999997);
INSERT INTO public.drugs4projet VALUES (7799957, 53.5999985);
INSERT INTO public.drugs4projet VALUES (3060296, 4.88000011);
INSERT INTO public.drugs4projet VALUES (3401604, 4.57000017);
INSERT INTO public.drugs4projet VALUES (3119262, 11.7299995);
INSERT INTO public.drugs4projet VALUES (3742274, 2.24000001);
INSERT INTO public.drugs4projet VALUES (7375708, 9.09000015);
INSERT INTO public.drugs4projet VALUES (7799940, 107.360001);


ALTER TABLE ONLY public.drugs4projet
    ADD CONSTRAINT drugs4projet_pkey PRIMARY KEY (cip);

--
-- PostgreSQL database dump complete
--
