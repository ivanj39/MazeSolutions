package webapp;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/** 
 * The MazeSolution class implements an solution that can to
 * simply solve this maze problem by finding the 
 * shortest route from the start to the finish  to the standard output.
 */
public class MazeService
{
    private int MAZE_WIDTH = -1;
    private int MAZE_HEIGHT = -1;
    private int MAZE_DEST_ROW = -1;
    private int MAZE_DEST_COL = -1;
    private int MAZE_START_ROW = -1;
    private int MAZE_START_COL = -1;
    private char MAZE_ROUTE_CHAR = '@';

    List<Object[]> rs = new ArrayList<Object[]>();
    
    /**
     *  Read a maze from file and convert into a char[][] array. 
     *
     * @param  fn   the file path passed as the argument
     * @return    mazeVO     
     */

	public MazeVO solveMaze(String mazeQuestion) {
		
		long st = (new Date()).getTime();
		MazeService ms = new MazeService();
		MazeVO mazeVO = new MazeVO();
        try {
        	mazeVO= ms.sM(mazeQuestion);
		} catch (Exception e) {			
			e.printStackTrace();
		}
        long et = (new Date()).getTime();
        System.out.println("Time taken in second(s):" + ((et - st) / 1000));
        
        return mazeVO;
       
	}




    public static void main(String[] args) throws Exception
    {
        long st = (new Date()).getTime();
        MazeService ms = new MazeService();
        ms.sM(args[0]);
        long et = (new Date()).getTime();
        System.out.println("Time taken in second(s):" + ((et - st) / 1000));
    }

    public MazeVO sM(String fn) throws Exception
    {
        int r = 0;
        char[][] mA;
        String da = "";
        List<String> dl = new ArrayList<String>();
        BufferedReader brMaze = new BufferedReader(new FileReader(new File(fn)));       

        while ((da = brMaze.readLine()) != null)
        {
            dl.add(da);
        }

        MAZE_HEIGHT = dl.size();
        mA = new char[MAZE_HEIGHT][];
        for (String d : dl)
        {
            int sp = d.indexOf("A");
            int ep = d.indexOf("B");
            if (sp > -1)
            {
                MAZE_WIDTH = d.length() - 1;
                MAZE_START_ROW = r;
                MAZE_START_COL = sp;
            }
            if (ep > -1)
            {
                MAZE_DEST_ROW = r;
                MAZE_DEST_COL = ep;
            }
            mA[r] = d.toCharArray();
            r++;
        }
        MazeVO mazeVO = sp(mA, MAZE_START_ROW, MAZE_START_COL);
        brMaze.close();
        return mazeVO;
    }

    private MazeVO sp(char[][] a, int r, int c)
    {
    	MazeVO mazeVO = new MazeVO();
    	char d = '-';
        N startNode = gd(a, r, c, '0', null);

        while ((d = startNode.nd(true)) != '-')
        {
            System.out.println("");
            fw(cA(a), startNode, d);
        }

        int mm = 0;
        String s = null;
        if (rs.size() > 0)
        {
            for (Object[] result : rs)
            {
                if (mm == 0 || mm > ((Integer) result[0]).intValue())
                {
                    //mm = (int) result[0];
                    mm =  ((Integer) result[0]).intValue();
                    s = (String) result[1];
                }
            }
            if (s != null)
            {
            	mazeVO = pm(cA(a), s, mm);
            }
        }
        else
        {
            System.out.println("Invalid Maze!");
        }
        
        return mazeVO;
    }

    private MazeVO pm(char[][] m, String s, int mm)
    {
        int c = 0;
        int r = 0;
        int nc = 0;
        int nr = 0;
        char d = '-';
        String st = "";
        String da[];
        String nda[];
        String[] mvs = s.split(";");

        for (int ctr = 0; ctr < mvs.length - 1; ctr++)
        {
            da = mvs[ctr].split(",");
            nda = mvs[ctr + 1].split(",");

            r = Integer.parseInt(da[0]);
            c = Integer.parseInt(da[1]);
            d = da[2].charAt(0);

            nr = Integer.parseInt(nda[0]);
            nc = Integer.parseInt(nda[1]);

            while (c != nc || r != nr)
            {
                if (d == 'R')
                {
                    c++;
                }
                else if (d == 'L')
                {
                    c--;
                }
                else if (d == 'U')
                {
                    r--;
                }
                else if (d == 'D')
                {
                    r++;
                }
                if (m[r][c] != 'B')
                {
                    m[r][c] = MAZE_ROUTE_CHAR;
                }
            }
        }
        for (int ctr = 0; ctr < MAZE_HEIGHT; ctr++)
        {
            st += String.valueOf(m[ctr]) + System.lineSeparator();
        }
        System.out.println(st);        
        System.out.println("No. Of Moves: " + mm);
        
        MazeVO mazeVO = new MazeVO();
        mazeVO.setResult(st);
        mazeVO.setTimeInSec(String.valueOf(mm).toString());        
        return mazeVO;
        
        
    }

    private void fw(char[][] a, N n, char d)
    {
        while (n != null)
        {
            int c = n.c;
            int r = n.r;
            N lj = n;
            boolean cm = false;

            while ((r != MAZE_DEST_ROW || c != MAZE_DEST_COL) && d != '-')
            {
                switch (d)
                {
                case 'R':
                    c = c + ((cm = cmd(a, r, c, d)) ? 1 : 0);
                    break;
                case 'L':
                    c = c - ((cm = cmd(a, r, c, d)) ? 1 : 0);
                    break;
                case 'U':
                    r = r - ((cm = cmd(a, r, c, d)) ? 1 : 0);
                    break;
                case 'D':
                    r = r + ((cm = cmd(a, r, c, d)) ? 1 : 0);
                    break;
                }

                if (cm)
                {
                    a[r][c] = a[r][c] == 'B' ? 'B' : MAZE_ROUTE_CHAR;

                    n = gd(a, r, c, d, lj);
                    if (n.ij(d))
                    {
                        lj = n;
                    }
                    n.sf(d, false);
                    continue;
                }

                while (lj != null)
                {
                    if ((d = lj.nd(true)) != '-')
                    {
                        c = lj.c;
                        r = lj.r;
                        break;
                    }
                    lj = lj.p;
                }
            }
            n = paGna(a, n);
            if (n == null || (n != null && n.c == MAZE_START_COL && n.r == MAZE_START_ROW))
            {
                break;
            }
            d = n.nd(true);
        }
    }

    private N paGna(char[][] a, N n)
    {
        int nm = 0;
        N nAn = null;
        String m = "";

        if (n.c == MAZE_DEST_COL && n.r == MAZE_DEST_ROW)
        {
            Object[] r = new Object[2];

            m = n.r + "," + n.c + "," + grfd(n) + ";";
            while (n.p != null)
            {
                nm += Math.abs(n.c - n.p.c) + Math.abs(n.r - n.p.r);
                m = n.p.r + "," + n.p.c + "," + grfd(n) + ";" + m;

                n = n.p;
                if (nAn == null && n.nd(false) != '-'
                        && a[n.r + (n.mu ? -1 : n.md ? 1 : 0)][n.c + (n.mr ? 1 : n.ml ? -1 : 0)] != MAZE_ROUTE_CHAR)
                {
                    nAn = n;
                }
            }
            r[0] = nm;
            r[1] = m;
            rs.add(r);
        }
        return nAn;
    }

    public char grfd(N n)
    {
        char d = '-';
        if (n.p.c == n.c && n.p.r > n.r)
        {
            d = 'U';
        }
        else
        {
            if (n.p.c == n.c && n.p.r < n.r)
            {
                d = 'D';
            }
            else
            {
                if (n.p.c < n.c && n.p.r == n.r)
                {
                    d = 'R';
                }
                else
                {
                    d = 'L';
                }
            }
        }
        return d;
    }

    private N gd(char[][] arr, int sr, int sc, char d, N pn)
    {
        N n = new N(sr, sc, pn);
        if (cmd(arr, sr, sc, 'R'))
        {
            n.mr = true;
        }
        if (cmd(arr, sr, sc, 'L'))
        {
            n.ml = true;
        }
        if (cmd(arr, sr, sc, 'U'))
        {
            n.mu = true;
        }
        if (cmd(arr, sr, sc, 'D'))
        {
            n.md = true;
        }

        if (pn != null)
        {
            pn.n = n;
            if ((d == 'L' && n.ml))
            {
                pn.ml = false;
            }
            if ((d == 'R' && n.mr))
            {
                pn.mr = false;
            }
            if ((d == 'U' && n.mu))
            {
                pn.mu = false;
            }
            if ((d == 'D' && n.md))
            {
                pn.md = false;
            }
        }
        return n;
    }

    private boolean cmd(char[][] a, int cr, int cc, char d)
    {
        if (d == 'R')
        {
            cc++;
        }
        else if (d == 'L')
        {
            cc--;
        }
        else if (d == 'U')
        {
            cr--;
        }
        else if (d == 'D')
        {
            cr++;
        }

        if (cc > (MAZE_WIDTH - 1) || cc < 0 || cr > (MAZE_HEIGHT - 1) || cr < 0 || iw(a[cr][cc]))
        {
            return false;
        }
        return true;
    }

    private boolean iw(char c)
    {
        if (c == '.' || c == 'B')
        {
            return false;
        }
        return true;
    }

    public char[][] cA(char[][] a)
    {
        char[][] b = new char[a.length][];
        for (int i = 0; i < a.length; i++)
        {
            b[i] = a[i].clone();
        }
        return b;
    }

    private class N
    {
        public int r = 0;
        public int c = 0;

        public boolean mr = false;
        public boolean ml = false;
        public boolean mu = false;
        public boolean md = false;

        public N p = null;
        public N n = null;

        public N(int cr, int cc, N pN)
        {
            r = cr;
            c = cc;
            p = pN;
        }

        public boolean ij(char cD)
        {
            char c = '-';
            int cn = (mr ? 1 : 0) + (mu ? 1 : 0) + (md ? 1 : 0) + (ml ? 1 : 0);

            if (cn > 1 || (cn == 1 && (c = mr ? 'R' : mu ? 'U' : md ? 'D' : ml ? 'L' : '-') != '-' && c != cD))
            {
                return true;
            }
            return false;
        }

        public char nd(boolean f)
        {
            char d = '-';
            if (mr)
            {
                d = 'R';
                mr = !f;
            }
            else if (ml)
            {
                d = 'L';
                ml = !f;
            }
            else if (md)
            {
                d = 'D';
                md = !f;
            }
            else if (mu)
            {
                d = 'U';
                mu = !f;
            }

            return d;
        }

        public void sf(char c, boolean f)
        {
            if (c == 'R')
            {
                mr = f;
            }
            else if (c == 'L')
            {
                ml = f;
            }
            if (c == 'D')
            {
                md = f;
            }
            if (c == 'U')
            {
                mu = f;
            }
        }
    }
}